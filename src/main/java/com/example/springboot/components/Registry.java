package com.example.springboot.components;

import com.example.springboot.entities.Address;
import com.example.springboot.exceptions.NoSuchUserException;
import com.example.springboot.exceptions.UnauthorizedUserCreationException;
import org.springframework.stereotype.Component;

import com.example.springboot.entities.User;
import com.example.springboot.interfaces.UserFinder;
import com.example.springboot.interfaces.UserRegistry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class Registry implements UserRegistry, UserFinder {
    private static final Pattern francePattern = Pattern.compile("^[Ff][Rr]([Aa][Nn][Cc][Ee])?$");

    private final List<User> users;

    public Registry() {
        users = new ArrayList<>();
    }

    /**
     * Check if the dateOfBirth matches an adult person
     */
    private boolean isAdult(Calendar dateOfBirth) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, -18);
        return dateOfBirth.before(c) || (
                dateOfBirth.get(Calendar.YEAR) == c.get(Calendar.YEAR) &&
                        dateOfBirth.get(Calendar.MONTH) == c.get(Calendar.MONTH) &&
                        dateOfBirth.get(Calendar.DAY_OF_MONTH) == c.get(Calendar.DAY_OF_MONTH)
                );
    }

    /**
     * Check if the address matches a person living in France
     */
    private boolean isFrench(Address address) {
        return francePattern.matcher(address.getCountry()).matches();
    }

    /**
     * Register a new user
     * @param user The user data
     * @return The id of the user inside the system
     * @throws UnauthorizedUserCreationException The age is below eighteen or the address is not in France
     */
    @Override
    public int registerUser(User user) throws UnauthorizedUserCreationException {
        if (!isAdult(user.getDateOfBrith()))
            throw new UnauthorizedUserCreationException("The user should be an adult");
        else if (!isFrench(user.getAddress()))
            throw new UnauthorizedUserCreationException("The user should live in France");

        users.add(user);
        user.setId(users.stream().map(User::getId).max(Integer::compareTo).get() + 1);
        return user.getId();
    }

    /**
     * @param id The id of a user
     * @return The user details
     * @throws NoSuchUserException The user was not found in the system
     */
    @Override
    public User getUserDetails(int id) throws NoSuchUserException {
        return users.stream().filter(user -> user.getId() == id).findFirst().orElseThrow(NoSuchUserException::new);
    }
}
