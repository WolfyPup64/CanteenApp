package com.classboxes.canteenapp.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.classboxes.canteenapp.model.Cart;
import com.classboxes.canteenapp.model.Food;
import com.classboxes.canteenapp.model.Order;
import com.classboxes.canteenapp.model.User;
import com.classboxes.canteenapp.model.Vendor;
import com.classboxes.canteenapp.repository.CartRepository;
import com.classboxes.canteenapp.repository.OrderRepository;
import com.classboxes.canteenapp.repository.UserRepository;
import com.classboxes.canteenapp.repository.VendorRepository;
import com.classboxes.canteenapp.service.CustomUserDetails;
import com.classboxes.canteenapp.service.FoodService;

@Controller
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
    @Autowired
    private FoodService foodService;
	@Autowired
	private VendorRepository vendorRepo;
	@Autowired
	private CartRepository cartRepo;
	@Autowired
	private OrderRepository orderRepo;
	org.slf4j.Logger logger = LoggerFactory.getLogger(UserController.class);
	
	boolean testVendor = false;
	
	@GetMapping("/wallet")
	public String viewWallet(Model model) {
		Object id = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long currentId = null;
		if(id instanceof CustomUserDetails) {
			currentId = ((CustomUserDetails)id).getId();
		}
		User currentUser = userRepo.findCurrentUser(currentId);
		model.addAttribute("currentUser", currentUser);
		return "add_money";
	}
	
	@PostMapping(value = "/money-added")
	public String processAddMoney(@ModelAttribute("money") float money, @RequestParam(value = "money") float amount, Model model) {
		Object id = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long currentId = null;
		if(id instanceof CustomUserDetails) {
			currentId = ((CustomUserDetails)id).getId();
		}
		userRepo.addToWallet(money, currentId);
		model.addAttribute("amount", amount);
		return "add";
	}
	
	public String viewHomePage() {
		return "index";
	}
	
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		
		return "signup_form";
	}
	
	@PostMapping("/process_register")
	public String processRegister(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		
		userRepo.save(user);
		
		return "register_success";
	}
	
	@GetMapping("/users")
	public String listUsers(Model model) {
		Object id = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long currentId = null;
		if(id instanceof CustomUserDetails) {
			currentId = ((CustomUserDetails)id).getId();
		}
		User currentUser = userRepo.findCurrentUser(currentId);
		model.addAttribute("currentUser", currentUser);
		
		return "users";
	}
	
	@GetMapping("/vendor-check")
	public String vendorCheck(Model model)
	{
		Object id = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long currentId = null;
		if(id instanceof CustomUserDetails) {
			currentId = ((CustomUserDetails)id).getId();
		}
		
		Vendor ven = vendorRepo.areYouAVendor(currentId);
		if(ven == null) 
		{ 
			User currentUser = userRepo.findCurrentUser(currentId); 
			model.addAttribute("currentUser", currentUser); 
			String warning = "You are not a vendor!!!";
			model.addAttribute("warning", warning);
			return "users"; 
		} 
		else 
		{ 
			return "vendor_login"; 
		}
		 
	}
	
	@PostMapping("/vendor-home")
	public String vendorPage(Model model, @RequestParam(value = "pass") int pass) 
	{
		
		if(pass == 987654321)
		{
			testVendor = true;
			return "vendor";
		}
		else
		{
			String warning = "Wrong password, please try again!!!";
			model.addAttribute("warning", warning);
			return "vendor_login"; 
		}
		
	}
	
	@RequestMapping(value = "/vendor")
	public String backToVendorHome(Model model) {
	    
		if(testVendor == true)
		{
			return "vendor";
		}
		else
		{
			Object id = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Long currentId = null;
			if(id instanceof CustomUserDetails) {
				currentId = ((CustomUserDetails)id).getId();
			}
			User currentUser = userRepo.findCurrentUser(currentId);
			model.addAttribute("currentUser", currentUser);
			String warning = "You have not login in as a vendor yet";
			model.addAttribute("warning", warning);
			return "users"; 
			
		}
		
	}
	
	@RequestMapping("/vendor-food")
	public String viewHomePage(Model model) {
		
		if(testVendor == true)
		{
		    List<Food> listFood = foodService.listAll();
		    model.addAttribute("listFood", listFood);
		     
		    return "food_index";
		}
		else
		{
			Object id = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Long currentId = null;
			if(id instanceof CustomUserDetails) {
				currentId = ((CustomUserDetails)id).getId();
			}
			User currentUser = userRepo.findCurrentUser(currentId);
			model.addAttribute("currentUser", currentUser);
			String warning = "You have not login in as a vendor yet";
			model.addAttribute("warning", warning);
			return "users"; 
			
		}
		

	}
	
	@RequestMapping("/new")
	public String showNewFoodPage(Model model) {
		if(testVendor == true)
		{
		    Food food = new Food();
		    model.addAttribute("food", food);
		     
		    return "new_food";
		}
		else
		{
			Object id = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Long currentId = null;
			if(id instanceof CustomUserDetails) {
				currentId = ((CustomUserDetails)id).getId();
			}
			User currentUser = userRepo.findCurrentUser(currentId);
			model.addAttribute("currentUser", currentUser);
			String warning = "You have not login in as a vendor yet";
			model.addAttribute("warning", warning);
			return "users"; 
			
		}
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveFood(Model model, @ModelAttribute("food") Food food) {
	    foodService.save(food);
	     
	    return "redirect:/vendor-food";
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView showEditProductPage(@PathVariable(name = "id") int id, Model model) {
		if(testVendor == true)
		{
			ModelAndView mav = new ModelAndView("edit_product");
		    Food food = foodService.get(id);
		    mav.addObject("food", food);
		     
		    return mav;
		}
		else
		{
			Object idTest = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Long currentId = null;
			if(idTest instanceof CustomUserDetails) {
				currentId = ((CustomUserDetails)idTest).getId();
			}
			User currentUser = userRepo.findCurrentUser(currentId);
			model.addAttribute("currentUser", currentUser);
			String warning = "You have not login in as a vendor yet";
			model.addAttribute("warning", warning);
			ModelAndView mav1 = new ModelAndView("users");
			return mav1; 
			
		}	    
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") int id, Model model) {
		if(testVendor == true)
		{
			foodService.delete(id);
		    return "redirect:/vendor-food";  
		}
		else
		{
			Object idTest = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Long currentId = null;
			if(idTest instanceof CustomUserDetails) {
				currentId = ((CustomUserDetails)idTest).getId();
			}
			User currentUser = userRepo.findCurrentUser(currentId);
			model.addAttribute("currentUser", currentUser);
			String warning = "You have not login in as a vendor yet";
			model.addAttribute("warning", warning);
			return "users"; 
			
		}    
	}
	
	@RequestMapping("/log")
	public String reset(HttpServletRequest request, HttpServletResponse response) {
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
		testVendor = false;
		return "index";
	}
	
	@RequestMapping("/food-list")
	public String viewFoodList(Model model) {
		
	    List<Food> listFood = foodService.listAll();
	    model.addAttribute("listFood", listFood);
	     
		return "food_list";
	}
	
	@RequestMapping("/add-to-cart")
	public String addCart(Model model, @RequestParam(value = "amount") int amount, @RequestParam(value = "fPrice") float fPrice, @RequestParam(value = "fName") String fName, @RequestParam(value = "fId") long fId) 
	
	{
		Object id = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long currentId = null;
		if(id instanceof CustomUserDetails) {
			currentId = ((CustomUserDetails)id).getId();
		}
	    List<Food> listFood = foodService.listAll();
	    model.addAttribute("listFood", listFood);
		
		String alert = amount + " " + fName + " has been added to your cart.";
		model.addAttribute("alert", alert);
		
		cartRepo.insertIntoCart(amount, fPrice, fPrice, fName, fId, currentId);
	     
		return "food_list";
	}
	
	@RequestMapping("/view-cart")
	public String viewCart(Model model)
	{
		Object id = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long currentId = null;
		if(id instanceof CustomUserDetails) {
			currentId = ((CustomUserDetails)id).getId();
		}
		
		Object total1 = cartRepo.total(currentId);

		List<Cart> listCart = cartRepo.inYourCart(currentId);
		
	    if(total1 == null)
	    {   	
	    	model.addAttribute("total", 0);
	    	return "view_cart";
	    }
	    else
	    {
	    	
	    	model.addAttribute("listCart", listCart);
	    	double total2 = (double) total1;
	    	float total = (float) total2;
	    	model.addAttribute("total", total);
	    	return "view_cart";
	    }
	}
	
	@RequestMapping("/update-amount")
	public String editAmount(Model model, @RequestParam(value = "amount") int amount, @RequestParam(value = "cartId") Long cartId) {
		cartRepo.updateCart(amount, cartId);
		return "redirect:/view-cart";
		
	}
	
	@RequestMapping("/delete-from-cart")
	public String deleteFromCart(@RequestParam(value = "cartId") Long cartId)
	{
		cartRepo.deleteFromCart(cartId);
		return "redirect:/view-cart";
	}
	
	
	@RequestMapping("/orders")
	public String orders(Model model)
	{
		if(testVendor == true)
		{    
		    List<Order> order = orderRepo.orders();
		    model.addAttribute("order", order);
			return "orders";
		}
		else
		{
			Object id = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Long currentId = null;
			if(id instanceof CustomUserDetails) {
				currentId = ((CustomUserDetails)id).getId();
			}
			User currentUser = userRepo.findCurrentUser(currentId);
			model.addAttribute("currentUser", currentUser);
			String warning = "You have not login in as a vendor yet";
			model.addAttribute("warning", warning);
			return "users"; 
			
		}
	}
	
	@RequestMapping("/cancel")
	public String cancelOrder(@RequestParam(value = "orderId") Long orderId, @RequestParam(value = "userId") Long userId, @RequestParam(value = "cost") float cost) {
		orderRepo.cancelOrder(orderId);
		userRepo.addToWallet(cost, userId);
		return "redirect:/orders";
	}
	
	@RequestMapping("/make-order")
	public String makeOrder(Model model)
	{
		Object id = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long currentId = null;
		if(id instanceof CustomUserDetails) {
			currentId = ((CustomUserDetails)id).getId();
		}
		float wallet = userRepo.findCurrentWallet(currentId);
		Object cost1 = cartRepo.total(currentId);
		int test = cartRepo.count(currentId);
		float cost = 0;
		if(cost1 != null)
		{
			double cost2 = (double) cost1;
			cost = (float) cost2;
		}

		
		List<Cart> listCart = cartRepo.inYourCart(currentId);
			
		if(wallet >= cost && test == 1)
		{
			Random random = new Random();
			model.addAttribute("order", "Your order has been made.");
	    	model.addAttribute("total", 0);
	    	userRepo.removeFromWallet(cost, currentId);
	    	long orderNumber = random.nextInt(1000000000);
	    	cartRepo.ordered(currentId, orderNumber);
	    	
	    	Date currentDate = new Date();
	    	String strDateFormat = "hh:mm:ss a";
	    	DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
	    	String formattedDate = dateFormat.format(currentDate);
	    	
	    	orderRepo.createOrder(cost, currentDate, orderNumber, currentId);
	    	return "view_cart";
		}
		else
		{
			model.addAttribute("order", "Your order has not been made. Either you don't have sufficent funds or your cart is empty. Please try again once you have fixed this.");
	    	model.addAttribute("listCart", listCart);
	    	model.addAttribute("total", cost);
	    	return "view_cart";
		}
	}
	
	@RequestMapping("/view")
	public String viewOrder(Model model, @RequestParam(value = "orderNumber") Long orderNumber, @RequestParam(value = "userId") Long userId)
	{
		if(testVendor == true)
		{    
			Order order = orderRepo.findOrder(orderNumber);
			model.addAttribute("order", order);
			List<Cart> cart = cartRepo.findCartItems(orderNumber);
			model.addAttribute("cart", cart);
			User user = userRepo.findCurrentUser(userId);
			model.addAttribute("user", user);
			return "view_order";
		}
		else
		{
			Object id = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Long currentId = null;
			if(id instanceof CustomUserDetails) {
				currentId = ((CustomUserDetails)id).getId();
			}
			User currentUser = userRepo.findCurrentUser(currentId);
			model.addAttribute("currentUser", currentUser);
			String warning = "You have not login in as a vendor yet";
			model.addAttribute("warning", warning);
			return "users"; 
		}
	}
	
	@RequestMapping("/accept")
	public String acceptOrder(@RequestParam(value = "orderId") Long orderId) {
		orderRepo.acceptedOrder(orderId);
		return "redirect:/orders";
	}
	
	@RequestMapping("/order-accepted")
	public String acceptedOrder(Model model)
	{
		if(testVendor == true)
		{    
		    List<Order> order = orderRepo.ordersAccepted();
		    model.addAttribute("order", order);
			return "orders_accepted";
		}
		else
		{
			Object id = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Long currentId = null;
			if(id instanceof CustomUserDetails) {
				currentId = ((CustomUserDetails)id).getId();
			}
			User currentUser = userRepo.findCurrentUser(currentId);
			model.addAttribute("currentUser", currentUser);
			String warning = "You have not login in as a vendor yet";
			model.addAttribute("warning", warning);
			return "users"; 
			
		}
	}
	
	@RequestMapping("/view-accepted")
	public String viewAcceptedOrder(Model model, @RequestParam(value = "orderNumber") Long orderNumber, @RequestParam(value = "userId") Long userId)
	{
		if(testVendor == true)
		{    
			Order order = orderRepo.findOrder(orderNumber);
			model.addAttribute("order", order);
			List<Cart> cart = cartRepo.findCartItems(orderNumber);
			model.addAttribute("cart", cart);
			User user = userRepo.findCurrentUser(userId);
			model.addAttribute("user", user);			
			return "view_order_accept";
		}
		else
		{
			Object id = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Long currentId = null;
			if(id instanceof CustomUserDetails) {
				currentId = ((CustomUserDetails)id).getId();
			}
			User currentUser = userRepo.findCurrentUser(currentId);
			model.addAttribute("currentUser", currentUser);
			String warning = "You have not login in as a vendor yet";
			model.addAttribute("warning", warning);
			return "users"; 
		}
	}

	@RequestMapping("/order-com")
	public String completedOrder(@RequestParam(value = "orderId") Long orderId) {
		orderRepo.completedOrder(orderId);
		return "redirect:/order-accepted";
	}
	
	@RequestMapping("/order-user")
	public String orderUser(Model model){
		Object id = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long currentId = null;
		if(id instanceof CustomUserDetails) {
			currentId = ((CustomUserDetails)id).getId();
		}
		
		List<Order> order = orderRepo.ordersCustomer(currentId);
		model.addAttribute("order", order);
		return"order_user";
	}
	
	@RequestMapping("/cancel-user")
	public String cancelOrderCustomer(@RequestParam(value = "orderId") Long orderId, @RequestParam(value = "userId") Long userId, @RequestParam(value = "cost") float cost) {
		orderRepo.cancelOrder(orderId);
		userRepo.addToWallet(cost, userId);
		return "redirect:/order-user";
	}
	
	@RequestMapping("/view-user")
	public String viewOrderUser(Model model, @RequestParam(value = "orderNumber") Long orderNumber)
	{
		Order order = orderRepo.findOrder(orderNumber);
		model.addAttribute("order", order);
		List<Cart> cart = cartRepo.findCartItems(orderNumber);
		model.addAttribute("cart", cart);
		return "view_order_user";
	}
	
	@RequestMapping("/order-accepted-user")
	public String orderUserAccepted(Model model){
		Object id = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long currentId = null;
		if(id instanceof CustomUserDetails) {
			currentId = ((CustomUserDetails)id).getId();
		}
		
		List<Order> order = orderRepo.ordersAcceptedCustomer(currentId);
		model.addAttribute("order", order);
		return"order_accepted_user";
	}
	
	@RequestMapping("/view-user-accepted")
	public String viewOrderUserAccepted(Model model, @RequestParam(value = "orderNumber") Long orderNumber)
	{
		Order order = orderRepo.findOrder(orderNumber);
		model.addAttribute("order", order);
		List<Cart> cart = cartRepo.findCartItems(orderNumber);
		model.addAttribute("cart", cart);
		return "view_order_user_accepted";
	}
	
	@RequestMapping("/order-completed-user")
	public String orderUserCompleted(Model model){
		Object id = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long currentId = null;
		if(id instanceof CustomUserDetails) {
			currentId = ((CustomUserDetails)id).getId();
		}
		
		List<Order> order = orderRepo.ordersCompletedCustomer(currentId);
		model.addAttribute("order", order);
		return"order_completed_user";
	}
	
	@RequestMapping("/view-user-completed")
	public String viewOrderUserCompleted(Model model, @RequestParam(value = "orderNumber") Long orderNumber)
	{
		Order order = orderRepo.findOrder(orderNumber);
		model.addAttribute("order", order);
		List<Cart> cart = cartRepo.findCartItems(orderNumber);
		model.addAttribute("cart", cart);
		return "view_order_user_completed";
	}
	
	@RequestMapping("/order-canceled-user")
	public String orderUserCanceled(Model model){
		Object id = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long currentId = null;
		if(id instanceof CustomUserDetails) {
			currentId = ((CustomUserDetails)id).getId();
		}
		
		List<Order> order = orderRepo.ordersCanceledCustomer(currentId);
		model.addAttribute("order", order);
		return"order_canceled_user";
	}

	@RequestMapping("/view-user-canceled")
	public String viewOrderUserCanceled(Model model, @RequestParam(value = "orderNumber") Long orderNumber)
	{
		Order order = orderRepo.findOrder(orderNumber);
		model.addAttribute("order", order);
		List<Cart> cart = cartRepo.findCartItems(orderNumber);
		model.addAttribute("cart", cart);
		return "view_order_user_canceled";
	}
	
	@RequestMapping("/edit-user")
	public String editUser(Model model)
	{
		Object id = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long currentId = null;
		if(id instanceof CustomUserDetails) {
			currentId = ((CustomUserDetails)id).getId();
		}
		User user = userRepo.findCurrentUser(currentId);
		model.addAttribute("user", user);
		return "edit_user";
	}
	
	@RequestMapping("/update-user")
	public String editedUser(Model model, @RequestParam(value = "firstName") String firstName, @RequestParam(value = "lastName") String lastName, @RequestParam(value = "address") String address, @RequestParam(value = "town") String town, @RequestParam(value = "state") String state)
	{
		Object id = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long currentId = null;
		if(id instanceof CustomUserDetails) {
			currentId = ((CustomUserDetails)id).getId();
		}
		userRepo.updateUser(firstName, lastName, address, town, state, currentId);
		return "redirect:/users";
	}
}

