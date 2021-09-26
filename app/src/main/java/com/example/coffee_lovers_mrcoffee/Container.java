package com.example.coffee_lovers_mrcoffee;

import com.example.coffee_lovers_mrcoffee.services.AuthService;
import com.example.coffee_lovers_mrcoffee.utils.ValidationUtils;

public class Container {

    private static Container _instant;
    public static Container instant() {
        if(_instant == null) {
            synchronized (Container.class) {
                if (_instant == null)
                    _instant = new Container();
            }
        }

        return _instant;
    }


    public final AuthService authService;
    public final ValidationUtils validationUtils;


    private Container(){
        authService = new AuthService();
        validationUtils = new ValidationUtils();
    }

}
