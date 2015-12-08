package NC_Lab1.view;

import NC_Lab1.controller.ClientController;
import NC_Lab1.controller.ServerController;
import java.util.ArrayList;
import java.util.Scanner;

public class View {
//
//    private static ServerController ctrl = ServerController.getInstance();
//    private static View view = new View();
//    private final Scanner scan = new Scanner(System.in);
//
//    private View() {
//    }
//
//    public static View getInstance() {
//        return view;
//    }
//
//    public void show_start() {
//        System.out.println("Добро пожаловать в справочную систему\n Музыкальной библиотеки!");
//    }
//
//    public int show_dialog_start() {
//        System.out.println("Введите номер действия(1,2,3,4,0):\n1)Поиск.\n2)Добавление.\n3)Удаление.\n4)Сохранение.\n0)Выйти из программы");
//        switch (scan.nextInt()) {
//            case 1:
//                show_dialog_Find();
//                break;
//            case 2:
//                show_dialog_Add();
//                break;
//            case 3:
//                show_dialog_Remove();
//                break;
//            case 4:
//                show_dialog_save();
//                break;
//            case 0:
//                return 0;
//            default:
//                show_error();
//                show_dialog_start();
//                break;
//        }
//        return 1;
//    }
//
//    public void show_dialog_Find() {
//        System.out.println("Что вы желаете найти?(1,2,3,4,0):\n1)Поиск треков.\n2)Поиск альбомов.\n3)Поиск исполнителей.\n4)Поиск жанров.\n0)Отмена.");
//        switch (scan.nextInt()) {
//            case 1:
//                show_dialog_Find_Track();
//                break;
//            case 2:
//                //show_dialog_Find_Album();
//                break;
//            case 3:
//                //show_dialog_Find_Artist();
//                break;
//            case 4:
//                show_dialog_Find_Genre();
//                break;
//            case 0:
//                show_dialog_start();
//                break;
//            default:
//                show_error();
//                show_dialog_Find();
//        }
//    }
//
//    public void show_dialog_Find_Track() {
//        System.out.println("Выберите метод поиска трека(1,2,3,4,5,6,0)\nиспользуйте  \"*\"-для замены любого символа\n\"?\"-для замены нескольких символов:\n1)Поиск по номеру трека.\n2)Поиск по названию трека.\n3)Поиск по исполнителю.\n4)Поиск по альбому.\n5)Поиск по жанру.\n6)Поиск всех треков.\n0)Отмена.");
//        ArrayList<String> arrayList = new ArrayList<String>();
//        switch (scan.nextInt()) {
//            case 1:
//                System.out.print("Введите номер трека: ");
//                arrayList=ctrl.findTrackById( scan.nextLong());
//                query_results_tracks(arrayList);
//                break;
//            case 2:
//                System.out.print("Введите название трека: ");
//                arrayList=ctrl.find_track(2, scan.next());
//                query_results_tracks(arrayList);
//                break;
//            case 3:
//                System.out.print("Введите название исполнителя: ");
//                arrayList=ctrl.find_track(3, scan.next());
//                query_results_tracks(arrayList);
//                break;
//            case 4:
//                System.out.print("Введите название альбома: ");
//                arrayList=ctrl.find_track(4, scan.next());
//                query_results_tracks(arrayList);
//                break;
//            case 5:
//                System.out.print("Введите название жанра: ");
//                arrayList=ctrl.find_track(5, scan.next());
//                query_results_tracks(arrayList);
//                break;
//            case 6:
//                arrayList=ctrl.find_track(6, "");
//                query_results_tracks(arrayList);
//                break;
//            case 0:
//                show_dialog_Find();
//                break;
//            default:
//                show_error();
//                show_dialog_Find_Track();
//                break;
//
//        }
//
//    }
//
//    public void show_dialog_Add() {
//        System.out.println("Введите способ добавления(1,2,3,4,0):\n1)Добавить новый трек.\n2)Добавить новый жанр.\n3)Добавить треки из файла.\n4)Добавить жанры из файла.\n0)Отмена.");
//        ArrayList<String> arrayList = new ArrayList<String>();
//        switch (scan.nextInt()) {
//            case 1:
//                System.out.print("Введите название трека: ");
//                arrayList.add(scan.next());
//                System.out.print("Введите название исполнителя: ");
//                arrayList.add(scan.next());
//                System.out.print("Введите название альбома: ");
//                arrayList.add(scan.next());
//                System.out.print("Введите длину записи(в секундах): ");
//                arrayList.add(scan.next());
//                System.out.print("Введите жанр трека: ");
//                arrayList.add(scan.next());
//                ctrl.add(1, arrayList);
//                break;
//            case 2:
//                System.out.print("Введите название жанра: ");
//                arrayList.add(scan.next());
//                ctrl.add(2, arrayList);
//                break;
//            case 3:
//                System.out.print("Введите название файла треков: ");
//                
//                ctrl.load(scan.next());
//                break;
////необходимо исправить реализацию
//            case 4:
//                System.out.print("Введите название файла жанров: ");
//                arrayList.add(scan.next());
//                ctrl.add(4, arrayList);
//                break;
//            case 0:
//                show_dialog_start();
//                break;
//            default:
//                show_error();
//                show_dialog_Add();
//                break;
//
//        }
//    }
//
//    public void show_dialog_Remove() {
//        System.out.println("Введите способ удаления(1,2,3,4,5,0):\n1)Удалить трек(по номеру).\n2)Удалить трек(по названию).\n3Удалить жанр.\n4)Удалить все треки.\n5)Удалить все жанры.\n0)Отмена.");
//        switch (scan.nextInt()) {
//            case 1:
//                System.out.print("Введите номер трека: ");
//                ctrl.remove(1, scan.next());
//                break;
//            case 2:
//                System.out.print("Введите название трека: ");
//                ctrl.remove(2, scan.next());
//                break;
//            case 3:
//                System.out.print("Введите название жанра: ");
//                ctrl.remove(3, scan.next());
//                break;
//            case 4:
//                System.out.print("Вы уверены, что хотите удалить все треки?(Y/N): ");
//                ctrl.remove(4, scan.next());
//                break;
//            case 5:
//                System.out.print("Вы уверены, что хотите удалить все жанры?(Y/N): ");
//                ctrl.remove(5, scan.next());
//                break;
//            case 0:
//                show_dialog_Find();
//                break;
//            default:
//                show_error();
//                show_dialog_Remove();
//        }
//
//    }
//
//    public void show_error() {
//        System.err.println("Выбран не верный номер, повторите попытку!");
//    }
//
//    
//    
//    
//    
////    private void show_dialog_Find_Album() {
////        System.out.println("Выберите метод поиска альбома(1,2,0)\nиспользуйте  \"*\"-для замены любого символа\n\"?\"-для замены нескольких символов:\n1)Поиск по номеру альбома.\n2)Поиск по названию альбома.\n0)Отмена.");
////        switch (scan.nextInt()) {
////            case 1:
////                System.out.print("Введите номер альбома: ");
////                ctrl.find_album(1, scan.next());
////                break;
////            case 2:
////                System.out.print("Введите название альбома: ");
////                ctrl.find_album(2, scan.next());
////                break;
////            case 0:
////                show_dialog_Find();
////                break;
////            default:
////                show_error();
////                show_dialog_Find_Album();
////                break;
////        }
////    }
//
////    private void show_dialog_Find_Artist() {
////        System.out.println("Выберите метод поиска исполнителя(1,2,0)\nиспользуйте  \"*\"-для замены любого символа\n\"?\"-для замены нескольких символов:\n1)Поиск по номеру исполнителя.\n2)Поиск по названию исполнителя.\n0)Отмена.");
////        switch (scan.nextInt()) {
////            case 1:
////                System.out.print("Введите номер альбома: ");
////                ctrl.find_album(1, scan.next());
////                break;
////            case 2:
////                System.out.print("Введите название альбома: ");
////                ctrl.find_album(2, scan.next());
////                break;
////            case 0:
////                show_dialog_Find();
////                break;
////            default:
////                show_error();
////                show_dialog_Find_Album();
////                break;
////        }
////    }
//
//    private void show_dialog_Find_Genre() {
//        ArrayList<String>arrayList = new ArrayList<String>();
//        System.out.println("Выберите метод поиска жанра(1,2,3,0)\nиспользуйте  \"*\"-для замены любого символа\n\"?\"-для замены нескольких символов:\n1)Поиск по номеру жанра.\n2)Поиск по названию жанра.\n3)Поиск всех.\n0)Отмена.");
//        switch (scan.nextInt()) {
//            case 1:
//                System.out.print("Введите номер жанра: ");
//                arrayList=ctrl.find_genre(1, scan.next());
//                query_results_genres(arrayList);
//                break;
//            case 2:
//                System.out.print("Введите название жанра: ");
//                arrayList=ctrl.find_genre(2, scan.next());
//                query_results_genres(arrayList);
//                break;
//            case 3:
//                arrayList=ctrl.find_genre(3, null);
//                query_results_genres(arrayList);
//                break;
//            case 0:
//                show_dialog_Find();
//                break;
//            default:
//                show_error();
//                show_dialog_Find_Genre();
//                break;
//        }
//    }
//
//    /**
//     * @param str your message
//     * @param error if "true", then RedText, if "false", then plain text
//     */
//    public void message(String str, boolean error) {
//
//        if (error) {
//            System.err.println(str);
//        } else {
//            System.out.println(str);
//        }
//
//    }
//
//    /**
//     * Выводим на экран один или несколько результатов запроса
//     *
//     * @param strings хранит в себе результаты запросов.
//     */
//    public void query_results_tracks(ArrayList<String> strings) {
//        System.out.println("Id \tНазвание трека \tИсполнитель \tАльбом \tПродолжительность трека \tЖанр");
//        for (String string : strings) {
//            System.out.println(string);
//        }
//    }
//
//    public void query_results_track(String str) {
//        System.out.println("Id \t Название трека \tИсполнитель \tАльбом \tПродолжительность трека \tЖанр\n" + str);
//    }
//
//    public void query_results_genres(ArrayList<String> strings) {
//        System.out.println("Название жанра:");
//        for (int i = 0; i < strings.size(); i++) {
//            System.out.println(strings.get(i));
//        }
//    }
//
//    public void show_dialog_save() {
//        System.out.println("Выберите действие (1,2,0):\n1)Сохранить добавленые треки в файл.\n2)Сохранить добавленые жанры в файл.\n0)Отмена.");
//        switch (scan.nextInt()) {
//            case 1:
//                System.out.print("Введите название файла: ");
//                ctrl.save(scan.next());
//                break;
//            case 0:
//                show_dialog_start();
//                break;
//            default:
//                show_error();
//                show_dialog_save();
//
//        }
//    }

}



