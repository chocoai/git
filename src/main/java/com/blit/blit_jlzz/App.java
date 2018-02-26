/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blit.blit_jlzz;

import com.jfinal.core.JFinal;

/**
 *
 * @author caibenxiang
 */
public class App {

    public static void main(String[] args) {
        JFinal.start("src/main/webapp", 8080, "/", 5);
    }

}
