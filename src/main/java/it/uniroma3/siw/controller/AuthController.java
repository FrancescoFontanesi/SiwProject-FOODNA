package it.uniroma3.siw.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.controller.validator.CredentialsValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.AdminService;
import it.uniroma3.siw.service.CredentialsService;
import jakarta.validation.Valid;

@Controller
@Validated
public class AuthController {

	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private AdminService adminService;
	
    @Autowired
    private CredentialsValidator credentialsValidator;


	@GetMapping("/login")
	public String showLoginForm() {
		return "login";
	}

	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("credentials", new Credentials());
		model.addAttribute("user", new User());
		return "formRegister";
	}

	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute("Credentials") Credentials c,
			@Valid @ModelAttribute("User") User u, @RequestParam("file") MultipartFile profilePic, BindingResult userBR,
			BindingResult credentialsBR,RedirectAttributes redirectAttributes) throws IOException {
        
		this.credentialsValidator.validate(c, credentialsBR);
		if (!userBR.hasErrors() && !credentialsBR.hasErrors()) {
			credentialsService.saveUserCredentials(c, u, profilePic);
			return "redirect:/login";

		}
		
        redirectAttributes.addAttribute("error", true);
		return "redirect:/register";
	}

	/*
	 * @PostMapping("/registration") public String showSpecificRoleRegister(Model
	 * model, @Valid @ModelAttribute("credentials") Credentials credentials,
	 * BindingResult result) {
	 * 
	 * this.credentialsValidator.validate(credentials, result);
	 * 
	 * System.out.println(credentials.toString());
	 * System.out.println(result.toString()); if (result.hasErrors()) { return
	 * "registration"; }
	 * 
	 * switch(credentials.getRole()) { case "Developer":
	 * model.addAttribute("developer", new Developer());
	 * model.addAttribute("credentials", credentials); return
	 * "formRegisterDeveloper"; case "Customer": model.addAttribute("customer", new
	 * Customer()); model.addAttribute("credentials", credentials); return
	 * "formRegisterCustomer"; default: model.addAttribute("error",
	 * "This role is not allowed"); return "registration"; }
	 * 
	 * }
	 * 
	 * 
	 * @GetMapping("/formRegisterDeveloper") public String
	 * showRegisterDeveloper(Model model, @ModelAttribute("developer") Developer
	 * developer, @ModelAttribute("credentials") Credentials
	 * credentials,@RequestParam("profilePic") MultipartprofilePic profilePic) {
	 * model.addAttribute("credentials", credentials);
	 * 
	 * // check if profilePic is empty if (profilePic.isEmpty()) {
	 * model.addAttribute("message", "Please select a profilePic to upload.");
	 * return "redirect:/"; }
	 * 
	 * // normalize the profilePic path String profilePicName =
	 * StringUtils.cleanPath(profilePic.getOriginalprofilePicname());
	 * 
	 * // save the profilePic on the local profilePic system try { Path path =
	 * Paths.get(UPLOAD_DIR + profilePicName);
	 * profilePics.copy(profilePic.getInputStream(), path,
	 * StandardCopyOption.REPLACE_EXISTING); } catch (IOException e) {
	 * e.printStackTrace(); } developer.setLogo("UPLOAD_DIR"+"profilePicName");
	 * model.addAttribute("developer", developer);
	 * System.out.println(developer.toString()); return "formRegisterDeveloper"; }
	 * 
	 * @PostMapping("/formRegisterDeveloper") public String registerDeveloper
	 * (@Valid @ModelAttribute("developer") Developer developer, BindingResult
	 * userBindingResult, @Valid
	 * 
	 * @ModelAttribute("credentials") Credentials credentials, BindingResult
	 * credentialsBindingResult,@RequestParam("profilePic") MultipartprofilePic
	 * profilePic, Model model) {
	 * 
	 * if (!profilePic.isEmpty()) { try { byte[] bytes = profilePic.getBytes(); Path
	 * path = Paths.get(UPLOADED_FOLDER_D + profilePic.getOriginalprofilePicname());
	 * profilePics.write(path, bytes); developer.setLogo("/images/sviluppatori/" +
	 * profilePic.getOriginalprofilePicname() ); } catch (IOException e) {
	 * e.printStackTrace();
	 * 
	 * model.addAttribute("message", "Failed to upload image"); return
	 * "formRegisterCustomer"; } }
	 * 
	 * if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
	 * 
	 * 
	 * userService.saveDev(developer); credentials.setUser(developer);
	 * credentialsService.saveCredentials(credentials);
	 * model.addAttribute("developer", developer); return "redirect:/login"; }
	 * return "formRegisterDeveloper";
	 * 
	 * }
	 * 
	 * @GetMapping("/formRegisterCustomer") public String showRegisterCustomer(Model
	 * model, @ModelAttribute("customer") Customer
	 * customer, @ModelAttribute("credentials") Credentials
	 * credentials,@RequestParam("profilePic") MultipartprofilePic profilePic) {
	 * model.addAttribute("credentials", credentials);
	 * 
	 * // check if profilePic is empty if (profilePic.isEmpty()) {
	 * model.addAttribute("message", "Please select a profilePic to upload.");
	 * return "redirect:/"; }
	 * 
	 * // normalize the profilePic path String profilePicName =
	 * StringUtils.cleanPath(profilePic.getOriginalprofilePicname());
	 * 
	 * // save the profilePic on the local profilePic system try { Path path =
	 * Paths.get(UPLOAD_DIR +"/"+ profilePicName);
	 * profilePics.copy(profilePic.getInputStream(), path,
	 * StandardCopyOption.REPLACE_EXISTING); } catch (IOException e) {
	 * e.printStackTrace(); }
	 * customer.setProprofilePicPic("UPLOAD_DIR"+"profilePicName");
	 * model.addAttribute("customer", customer);
	 * System.out.println(customer.toString()); return "formRegisterCustomer"; }
	 * 
	 * @PostMapping("/formRegisterCustomer") public String registerCustomer
	 * (@Valid @ModelAttribute("customer") Customer customer, BindingResult
	 * userBindingResult, @Valid
	 * 
	 * @ModelAttribute("credentials") Credentials credentials, BindingResult
	 * credentialsBindingResult,@RequestParam("profilePic") MultipartprofilePic
	 * profilePic, Model model) {
	 * 
	 * 
	 * if (!profilePic.isEmpty()) { try { byte[] bytes = profilePic.getBytes(); Path
	 * path = Paths.get(UPLOADED_FOLDER_C + profilePic.getOriginalprofilePicname());
	 * profilePics.write(path, bytes);
	 * customer.setProprofilePicPic("/images/profili/" +
	 * profilePic.getOriginalprofilePicname() ); } catch (IOException e) {
	 * e.printStackTrace();
	 * 
	 * model.addAttribute("message", "Failed to upload image"); return
	 * "formRegisterCustomer"; } }
	 * 
	 * if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
	 * userService.saveCustomer(customer); credentials.setUser(customer);
	 * credentialsService.saveCredentials(credentials);
	 * model.addAttribute("customer", customer);
	 * 
	 * return "redirect:/login";
	 * 
	 * } return "formRegisterCustomer";
	 * 
	 * }
	 */

	@GetMapping("/myProfile")
	public String myPage(Authentication auth, Model model) {

		Credentials c = credentialsService.getCredentials(auth.getName());
		User u = c.getUser();
		model.addAttribute("user", u);

		switch (c.getRole()) {
		case Credentials.COOK_ROLE:	
			return "cookPersonalPage";
		case Credentials.ADMIN_ROLE:
			adminService.loadAllUserCredentials(model);
			return "adminPage";
		default:
			return "redirect:/login";
		}
	}

}
