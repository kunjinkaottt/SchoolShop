package com.hzj;

public class test2 {
    public static boolean isRegularMatching(String rule, String str) {
        int lRule = rule.length();
        int lStr = str.length();
        int iRule = 0;
        int iStr = 0;
        while (iRule < lRule && iStr < lStr) {
            switch (rule.charAt(iRule)){
                case '*': {
                    iRule += 1;
                    if (iRule >= lRule) {
                        return true;
                    } else {
                        for (int i = iStr; i < lStr; i++) {
                            if (rule.substring(iRule, lRule).equals(str.substring(lStr - lRule + iRule, lStr)) ) {
                                return true;
                            }
                        }
                    }
                    break;
                }
                case '$': {
                    if ( str.charAt(iStr) > '9' || str.charAt(iStr) < '0') {
                        return false;
                    }
                    while ((iStr < lStr) && (str.charAt(iStr) >= '0') && (str.charAt(iStr) <= '9')) {
                        iStr += 1;
                    }
                    iRule += 1;
                    break;
                }
                default: {
                    if (rule.charAt(iRule) != str.charAt(iStr)) {
                        return false;
                    }
                    iRule += 1;
                    iStr += 1;
                    break;
                }
            }
        }
        if (iRule < lRule && iStr >= lStr) {
            if (rule.charAt(iRule) == '*') {
                return true;
            }
        } else {
            return  false ;
        }
        return false;
    }
}
