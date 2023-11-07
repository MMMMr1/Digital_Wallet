package com.michalenok.wallet.validator.util;

public final class ValidationConstant {
    public static final String REGEXP_VALIDATE_EMAIL = "^(?=.{6,}$)[\\s]*[a-zA-Z0-9]+([!\"#$%&'()*+,\\-.\\/:;<=>?\\[\\]\\^_{}][a-zA-z0-9]+)*@([\\w]+(-[\\w]+)?)(\\.[\\w]+[-][\\w]+)*(\\.[a-z]{2,})+[\\s]*$";
    public static final String REGEXP_VALIDATE_PHONE_NUMBER = "^(?=.{0,}$)[0-9]*$";

}
