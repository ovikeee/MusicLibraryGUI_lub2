/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NC_Lab1;

import NC_Lab1.GUI.GUI;
/**
 *
 * @author User
 */
public class Client {

    private static GUI gui;
    
    
//    private static DataInputStream in;         //поток ввода
//    private static DataOutputStream out;       //поток вывода
//    private static ObjectOutputStream oout;
//    private static ObjectInputStream oin;

//    private void setConnection(String address, int serverPort) {
//        try {
// 
//
//            // Создаем поток приема сообщений от сервера
//            // Поток создается, что бы можно было мгновенно получать сообщения от сервера.
///*        new Thread( new Runnable() {        //мгновенный прием сообщений от сервера
//             public void run() {    
//             try {
//             while (!interrupted() ) {        //цикл до прерывания потока (прерывание вызываем выше) 
//             temp.append(in.readUTF()).append("\n");//некий буфер, которы хранит историю сообщений
//             textOUTPUT.setText(temp.toString()); //textOUTPUT - это переменная компонента графического интерфейса, оно отвечает за то, что показывается в окошке вывода
//             }
//             } catch (IOException ex) {
//             dispose();           //закрываем окошко
//             }
//             }
//             }).start();//не забываем запустить его
//             */
//        } catch (IOException x) {
//            x.printStackTrace();
//        }
//    }
//
//    public enum NumberOperation {
//        addTrack, addGenre,
//        findTrackById, findTrackByName, findTrackByGenre, findTrackByArtist, findTrackByAlbum,
//        findGenreById, findGenreByName, 
//        findAllTrack, findAllGenre,
//        loadTrackAndGenre, saveTrackAndGenre,
//        removeTrackById, removeGenreById, removeGenreByName, removeAllTrack, removeAllGenre,
//        updateTrack, updateGenre,;
//    };

    

    /**
     * Удаленый вызов методов
     *
     * numOp код метода, который нужно вызвать. param параметры return
     * ArrayList<String> получаем массив строк. также принимается код с
     * информацией о выполнении операции: 1-успех, -1-ошибка
     *
     * @param numOp
     * @param param
     * @return
     */
    //метод, который получает что-либо от сервера
//    public synchronized static ArrayList<String> remoteMethode(NumberOperation numOp, ArrayList<String> param) {
//        ArrayList<String> answer = new ArrayList<String>();
//        try {
//            
//            oout.writeObject(numOp);
//            oout.writeObject(param);
//            
//            if (oin.readInt() == -1) {
//                gui.errorMessage("Операция не выполнена");
//            }
//            answer = (ArrayList<String>) oin.readObject();
//        } catch (IOException ex) {
//            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return answer;
//    }
//
//    public synchronized static ArrayList<String> remoteMethode(NumberOperation numOp, String param) {
//        ArrayList<String> answer = new ArrayList<String>();
//        try {
//            oout.writeObject(numOp);
//            out.writeUTF(param);
//            
//            if (in.readInt() == -1) {
//                gui.errorMessage("Операция не выполнена");
//            }
//            
//            answer = (ArrayList<String>) oin.readObject();
//        } catch (IOException ex) {
//            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return answer;
//    }
//
//    public synchronized static void remoteMethodeVoid(NumberOperation numOp, String param) {
//        try {
//            oout.writeObject(numOp);
//            out.writeUTF(param);
//            
//        } catch (IOException ex) {
//            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public synchronized static void remoteMethodeVoid(NumberOperation numOp, ArrayList<String> param) {
//        try {
//            oout.writeObject(numOp);
//            oout.writeObject(param);
//            
//        } catch (IOException ex) {
//            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    public static void main(String[] args) {

        Client main = new Client();

//        main.setConnection(address, serverPort);

        gui = new GUI();
        gui.setVisible(true);
       // gui.startState();//загружает какой-то дефолтный файл

    }
}
