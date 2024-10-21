package com.pmb.paymybuddy.controller;

import com.pmb.paymybuddy.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Manages user connections actions
 */
@Controller
@RequiredArgsConstructor
public class UserConnectionsController {
    private static final Logger logger = LoggerFactory.getLogger(UserConnectionsController.class);

    private final IUserService userService;

    private static final String REDIRECT_USERCONNECTIONS = "redirect:/userconnections";

    private static final String ERROR_ATTRIBUTE = "error";

    /**
     * Gets the user connection page
     *
     * @return Path to the user connection page
     */
    @GetMapping("/userconnections")
    public String getConnections() {
        logger.info("Get user connections form page");
        return "userconnections";
    }

    /**
     * Tries to add a new user connection
     *
     * @param email Email of the user currently being added
     * @param user Currently logged in user
     * @return Path of the redirected page
     */
    @PostMapping("/adduserconnection")
    public ModelAndView addUserConnection(@RequestParam String email, @AuthenticationPrincipal User user) {

        var currentUserOptional = userService.getUserByUsername(user.getUsername());

        if (currentUserOptional.isEmpty()){
            logger.error("Cannot get current user infos");
            var modelAndView = new ModelAndView("redirect:/login");
            modelAndView.addObject(ERROR_ATTRIBUTE, "Veuillez vous reconnecter");
            return modelAndView;
        }

        var currentUser = currentUserOptional.get();

        if (currentUser.getEmail().equals(email)) {
            logger.error("User cannot add themselves to their user connections");
            var modelAndView = new ModelAndView(REDIRECT_USERCONNECTIONS);
            modelAndView.addObject(ERROR_ATTRIBUTE, "Vous ne pouvez pas vous ajouter vous meme");
            return modelAndView;
        }

        if (currentUser.getConnections().stream().anyMatch(c -> c.getEmail().equals(email))) {
            logger.error("User already added as connection");
            var modelAndView = new ModelAndView(REDIRECT_USERCONNECTIONS);
            modelAndView.addObject(ERROR_ATTRIBUTE, "Vous avez déjà ajouté cet utilisateur");
            return modelAndView;
        }

        var userToAddOptional = userService.getUserByEmail(email);

        if (userToAddOptional.isEmpty()){
            logger.error("User to add does not exist");
            var modelAndView = new ModelAndView(REDIRECT_USERCONNECTIONS);
            modelAndView.addObject(ERROR_ATTRIBUTE, "Cet utilisateur n'existe pas");
            return modelAndView;
        }

        currentUser.getConnections().add(userToAddOptional.get());
        userService.updateUserConnections(currentUser);

        return new ModelAndView(REDIRECT_USERCONNECTIONS);
    }
}
