/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NC_Lab1.Model;

import java.util.Comparator;

/**
 *
 * @author User
 */
public class TrackComparatorById implements Comparator<Track> {

    /**
     * Если t > t1, возвращаем 1, если t < t1, возвращаем -1, иначе они равны
     * ивозвращаем 0.
     *
     * @param t первый операнд
     * @param t1 второй операнд
     * @return Если t > t1, возвращаем 1, если t < t1, возвращаем -1, иначе они
     * равны ивозвращаем 0.
     */
    @Override
    public int compare(Track t, Track t1) {
        if (t.getId() > t1.getId()) {
            return 1;
        }
        if (t.getId() < t1.getId()) {
            return -1;
        }
        return 0;
    }

}
