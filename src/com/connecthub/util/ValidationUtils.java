package com.connecthub.util;

public final class ValidationUtils {
    private static final String EMAIL_FORMAT = "^[a-zA-Z][\\w.-]*@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,6}$";
    private static final String USERNAME_FORMAT = "^[a-zA-Z][a-zA-Z0-9._]{2,14}$";

    private ValidationUtils() {
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_FORMAT);
    }

    public static boolean isValidUsername(String username) {
        return username != null && username.matches(USERNAME_FORMAT);
    }

    public static boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        return password.matches(".*[a-z].*")
                && password.matches(".*[A-Z].*")
                && password.matches(".*\\d.*")
                && password.matches(".*[@$!%*?&].*");
    }

    public static boolean isValidDate(String year, String month, String day) {
        try {
            int parsedYear = Integer.parseInt(year);
            int parsedMonth = Integer.parseInt(month);
            int parsedDay = Integer.parseInt(day);
            return parsedYear >= 1900
                    && parsedYear <= 2024
                    && parsedMonth >= 1
                    && parsedMonth <= 12
                    && parsedDay >= 1
                    && parsedDay <= 31;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
