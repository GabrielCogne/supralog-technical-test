package com.example.springboot.components;

import com.example.springboot.entities.Address;
import com.example.springboot.exceptions.NoSuchUserException;
import com.example.springboot.exceptions.UnauthorizedUserCreationException;
import com.example.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.springboot.entities.User;
import com.example.springboot.interfaces.UserFinder;
import com.example.springboot.interfaces.UserRegistry;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.regex.Pattern;

@Component
public class Registry implements UserRegistry, UserFinder {
    /**
     * A pattern to detect if a string matches "France" or "FR"
     */
    private static final Pattern FRANCE_PATTERN = Pattern.compile("^[Ff][Rr]([Aa][Nn][Cc][Ee])?$");

    /**
     * Access to the user database
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Check if the dateOfBirth matches an adult person
     */
    private boolean isAdult(LocalDate dateOfBirth) {
        LocalDate majorityBirthday = LocalDate.now().minusYears(18);
        return dateOfBirth.isBefore(majorityBirthday) || dateOfBirth.isEqual(majorityBirthday);
    }

    /**
     * Check if the address matches a person living in France
     */
    private boolean isFrench(Address address) {
        return FRANCE_PATTERN.matcher(address.getCountry()).matches();
    }

    /**
     * Register a new user
     * @param user The user data
     * @return The id of the user inside the system
     * @throws UnauthorizedUserCreationException The age is below eighteen or the address is not in France
     */
    @Override
    public Object registerUser(User user, boolean unfold) throws UnauthorizedUserCreationException {
        if (!isAdult(user.getDateOfBrith()))
            throw new UnauthorizedUserCreationException("The user should be an adult");
        else if (!isFrench(user.getAddress()))
            throw new UnauthorizedUserCreationException("The user should live in France");

        userRepository.save(user);
        return unfold ? user : user.getId();
    }

    /**
     * @param id The id of a user
     * @return The user details
     * @throws NoSuchUserException The user was not found in the system
     */
    @Override
    public User getUserDetails(BigInteger id) throws NoSuchUserException {
        return userRepository.findById(id).orElseThrow(NoSuchUserException::new);
    }
}
