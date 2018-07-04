/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.helpers;

/**
 *
 * @author Przemek
 */
public class Debuger {
    static boolean ifDisplay = false;
    public static void println(String toPrint){
        if(ifDisplay){
            System.out.println(toPrint);
        }
    }
}
