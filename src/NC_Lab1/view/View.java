package NC_Lab1.view;

import NC_Lab1.Util.FileManager;
import NC_Lab1.controller.ClientController;
import NC_Lab1.controller.ServerController;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class View {

    private static ServerController ctrl = new ServerController(new FileManager("default"));//ServerController.getInstance();
    private static View view = new View();
    private final Scanner scan = new Scanner(System.in);

    private View() {
    }

    public static View getInstance() {
        return view;
    }

    public void show_start() {
        System.out.println("Добро пожаловать в справочную систему\n Музыкальной библиотеки!");
    }

    public int show_dialog_start() {
        System.out.println("Введите номер действия(1,2,3,4,0):\n1)Поиск.\n2)Добавление.\n3)Удаление.\n4)Сохранение.\n0)Выйти из программы");
        switch (scan.nextInt()) {
            case 1:
                show_dialog_Find();
                break;
            case 2:
                show_dialog_Add();
                break;
            case 3:
                show_dialog_Remove();
                break;
            case 4:
                show_dialog_save();
                break;
            case 0:
                return 0;
            default:
                show_error();
                show_dialog_start();
                break;
        }
        return 1;
    }

    public void show_dialog_Find() {
        System.out.println("Что вы желаете найти?(1,2,3,4,0):\n1)Поиск треков.\n2)Поиск альбомов.\n3)Поиск исполнителей.\n4)Поиск жанров.\n0)Отмена.");
        switch (scan.nextInt()) {
            case 1:
                show_dialog_Find_Track();
                break;
            case 2:
                System.out.println("Введите название альбома: ");
                query_results_tracks(ctrl.findTrackByAlbum(scan.next()));
                break;
            case 3:
                System.out.println("Введите название исполнителя: ");
                query_results_tracks(ctrl.findTrackByArtist(scan.next()));
                break;
            case 4:
                show_dialog_Find_Genre();
                break;
                case 5: 
                    System.out.println("Введите название исполнителя: ");
                query_results_tracks(ctrl.findTrackByLength(scan.nextLong()));
                break;                    
            case 0:
                show_dialog_start();
                break;
            default:
                show_error();
                show_dialog_Find();
        }
    }
    
    public void show_dialog_Find_Track() {
        System.out.println("Выберите метод поиска трека(1,2,3,4,5,6,0)\nиспользуйте  \"*\"-для замены любого символа\n\"?\"-для замены нескольких символов:\n1)Поиск по номеру трека.\n2)Поиск по названию трека.\n3)Поиск по исполнителю.\n4)Поиск по альбому.\n5)Поиск по жанру.\n6)Поиск всех треков.\n0)Отмена.");
        ArrayList<String> arrayList = new ArrayList<String>();
        switch (scan.nextInt()) {
            case 1:
                System.out.print("Введите номер трека: ");
                arrayList.add(ctrl.findTrackById(scan.nextLong()));
                query_results_tracks(arrayList);
                break;
            case 2:
                System.out.print("Введите название трека: ");
                arrayList.addAll(ctrl.findTrackByTitle(scan.next()));
                query_results_tracks(arrayList);
                break;
            case 3:
                System.out.print("Введите название исполнителя: ");
                arrayList.addAll(ctrl.findTrackByArtist(scan.next()));
                query_results_tracks(arrayList);
                break;
            case 4:
                System.out.print("Введите название альбома: ");
                arrayList.addAll(ctrl.findTrackByAlbum(scan.next()));
                query_results_tracks(arrayList);
                break;
            case 5:
                System.out.print("Введите название жанра: ");
                arrayList.addAll(ctrl.findTrackByGenre(scan.next()));
                query_results_tracks(arrayList);
                break;
            case 6:
                arrayList.addAll(ctrl.findAllTracks());
                query_results_tracks(arrayList);
                break;
            case 0:
                show_dialog_Find();
                break;
            default:
                show_error();
                show_dialog_Find_Track();
                break;
        }show_dialog_Find_Track();
    }

    public void show_dialog_Add() {
        System.out.println("Введите способ добавления(1,2,3,4,0):\n1)Добавить новый трек.\n2)Добавить новый жанр.\n3)Загрузить файл.\n4)Загрузить файл по умолчанию.\n0)Отмена.");
        ArrayList<String> arrayList = new ArrayList<String>();
        switch (scan.nextInt()) {
            case 1:
                System.out.print("Введите название трека: ");
                arrayList.add(scan.next());
                System.out.print("Введите название исполнителя: ");
                arrayList.add(scan.next());
                System.out.print("Введите название альбома: ");
                arrayList.add(scan.next());
                System.out.print("Введите длину записи(в секундах): ");
                arrayList.add(scan.next());
                System.out.print("Введите жанр трека: ");
                arrayList.add(scan.next());
                ctrl.addTrack(arrayList);
                break;
            case 2:
                System.out.print("Введите название жанра: ");
                ctrl.addGenre(scan.next());
                break;
            case 3:
                System.out.print("Введите название файла: ");
                ctrl.load(scan.next());
                break;
            case 4:
                ctrl.load("default.muslib");
                break;
            case 0:
                show_dialog_start();
                break;
            default:
                show_error();
                show_dialog_Add();
                break;

        }
    }

    public void show_dialog_Remove() {
        System.out.println("Введите способ удаления(1,2,3,4,0):\n1)Удалить трек(по номеру).\n2Удалить жанр(по названию).\n3)Удалить все треки.\n4)Удалить все жанры.\n0)Отмена.");
        switch (scan.nextInt()) {
            case 1:
                System.out.print("Введите номер трека: ");
                ctrl.removeTrackById(scan.nextLong());
                break;
            case 2:
                System.out.print("Введите название жанра: ");
                ctrl.removeGenreByTitle(scan.next());
                break;
            case 3:
                System.out.print("Вы уверены, что хотите удалить все треки?(Y/N): ");
                if (scan.next().equalsIgnoreCase("y")) {
                    ctrl.removeAllTracks();
                    System.out.println("Все треки удалены");
                }
                break;
            case 4:
                System.out.print("Вы уверены, что хотите удалить все жанры?(Y/N): ");
                if (scan.next().equalsIgnoreCase("y")) {
                    ctrl.removeAllGenres();
                    System.out.println("Все жанры удалены");
                }
                break;
            case 0:
                show_dialog_Find();
                break;
            default:
                show_error();
                show_dialog_Remove();
        }

    }

    public void show_error() {
        System.err.println("Выбран не верный номер, повторите попытку!");
    }

//    private void show_dialog_Find_Album() {
//        System.out.println("Выберите метод поиска альбома(1,2,0)\nиспользуйте  \"*\"-для замены любого символа\n\"?\"-для замены нескольких символов:\n1)Поиск по номеру альбома.\n2)Поиск по названию альбома.\n0)Отмена.");
//        switch (scan.nextInt()) {
//            case 1:
//                System.out.print("Введите номер альбома: ");
//                ctrl.find_album(1, scan.next());
//                break;
//            case 2:
//                System.out.print("Введите название альбома: ");
//                ctrl.find_album(2, scan.next());
//                break;
//            case 0:
//                show_dialog_Find();
//                break;
//            default:
//                show_error();
//                show_dialog_Find_Album();
//                break;
//        }
//    }
//    private void show_dialog_Find_Artist() {
//        System.out.println("Выберите метод поиска исполнителя(1,2,0)\nиспользуйте  \"*\"-для замены любого символа\n\"?\"-для замены нескольких символов:\n1)Поиск по номеру исполнителя.\n2)Поиск по названию исполнителя.\n0)Отмена.");
//        switch (scan.nextInt()) {
//            case 1:
//                System.out.print("Введите номер альбома: ");
//                ctrl.find_album(1, scan.next());
//                break;
//            case 2:
//                System.out.print("Введите название альбома: ");
//                ctrl.find_album(2, scan.next());
//                break;
//            case 0:
//                show_dialog_Find();
//                break;
//            default:
//                show_error();
//                show_dialog_Find_Album();
//                break;
//        }
//    }
    private void show_dialog_Find_Genre() {
        System.out.println("Выберите метод поиска жанра(1,2,3,0)\nиспользуйте  \"*\"-для замены любого символа\n\"?\"-для замены нескольких символов:\n1)Поиск по номеру жанра.\n2)Поиск по названию жанра.\n3)Поиск всех.\n0)Отмена.");
        switch (scan.nextInt()) {
            case 2:
                System.out.print("Введите название жанра: ");
                query_results_genres(ctrl.findGenreByTitle(scan.next()));
                break;
            case 3:
                query_results_genres(ctrl.findAllGenre());
                break;
            case 0:
                show_dialog_Find();
                break;
            default:
                show_error();
                show_dialog_Find_Genre();
                break;
        }
    }

    /**
     * @param str your message
     * @param error if "true", then RedText, if "false", then plain text
     */
    public void message(String str, boolean error) {

        if (error) {
            System.err.println(str);
        } else {
            System.out.println(str);
        }

    }

    /**
     * Выводим на экран один или несколько результатов запроса
     *
     * @param strings хранит в себе результаты запросов.
     */
    public void query_results_tracks(ArrayList<String> strings) {
        System.out.println("Id \tНазвание трека \tИсполнитель \tАльбом \tПродолжительность трека \tЖанр");
        for (String string : strings) {
            System.out.println(string);
        }
    }

    public void query_results_track(String str) {
        System.out.println("Id \t Название трека \tИсполнитель \tАльбом \tПродолжительность трека \tЖанр\n" + str);
    }

    public void query_results_genres(String strings) {
        System.out.println("Id \t Название жанра");
        System.out.println(strings);
    }

    public void query_results_genres(ArrayList<String> strings) {
        System.out.println("Id \t Название жанра");
        for (String string : strings) {
            System.out.println(string);
        }
    }

    public void show_dialog_save() {
        System.out.println("Выберите действие (1,0):\n1)Сохранить добавленые треки в файл.\n0)Отмена.");
        switch (scan.nextInt()) {
            case 1:
                System.out.print("Введите название файла: ");
                ctrl.save(scan.next());
                break;
            case 0:
                show_dialog_start();
                break;
            default:
                show_error();
                show_dialog_save();

        }
    }

}
