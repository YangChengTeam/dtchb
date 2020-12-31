package com.yc.redevenlopes.utils;



public class UtilsBridge {


    static ShellUtils.CommandResult execCmd(final String command, final boolean isRooted) {
        return ShellUtils.execCmd(command, isRooted);
    }

}
