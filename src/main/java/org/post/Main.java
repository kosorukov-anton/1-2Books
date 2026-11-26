package org.post;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void printAll(Book[] books) {
        if (books == null || books.length == 0) {
            System.out.println("Список книг пуст.");
            return;
        }
        for (Book book : books) {
            if (book != null) {
                book.print();
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        // 1. Тест
        Publisher[] publishers = new Publisher[]{
                new Publisher("Проспект", "Москва"),
                new Publisher("Питер", "Санкт-Петербург"),
                new Publisher("БХВ", "Санкт-Петербург"),
                new Publisher("Диалектика", "Киев")
        };

        Book[] books = new Book[]{
                new Book("Computer Science: основы программирования на Java, ООП, алгоритмы и структуры данных",
                        new String[]{"Седжвик Роберт", "Уэйн Кевин"},2018, publishers[1]),
                new Book("Разработка требований к программному обеспечению. 3-е издание, дополненное",
                        "Вигерс Карл",2019,publishers[2]),
                new Book("Java. Полное руководство, 10-е издание","Шилдт Герберт",2018,publishers[3]),
                new Book("C/C++. Процедурное программирование","Полубенцева М.И.",2017,publishers[2]),
                new Book("Конституция РФ",2020,publishers[0])
        };

        // *******************************************интерфейс
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            System.out.print("Введите команду (create, print, exit): ");
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "create":
                    System.out.print("Какой объект необходимо создать (p/b): ");
                    String type = scanner.nextLine().trim().toLowerCase();

                    if (type.equals("p")) {
                        Publisher newPublisher = createPublisher(scanner);
                        if (newPublisher != null) {
                            publishers = addPublisher(publishers, newPublisher);
                            System.out.println("Издательство добавлено: " + newPublisher.getName());
                        }
                    } else if (type.equals("b")) {
                        Book newBook = createBook(scanner, publishers);
                        if (newBook != null) {
                            books = addBook(books, newBook);
                            System.out.println("Книга добавлена: " + newBook.getTitle());
                        }
                    } else {
                        System.out.println("Неверный тип объекта. Введите 'p' для издательства или 'b' для книги.");
                    }
                    break;

                case "print":
                    printAll(books);
                    break;

                case "exit":
                    System.out.println("Программа завершена.");
                    isRunning = false;
                    break;

                default:
                    System.out.println("Неизвестная команда. Доступные: create, print, exit.");
                    break;
            }
        }

        scanner.close();
    }

    // Метод создания издательства
    private static Publisher createPublisher(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Введите название издательства: ");
                String name = scanner.nextLine();

                System.out.print("Введите город издательства: ");
                String city = scanner.nextLine();

                return new Publisher(name, city);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage() + ". Попробуйте снова.");
            }
        }
    }

    // Метод создания книги
    private static Book createBook(Scanner scanner, Publisher[] publishers) {
        if (publishers == null || publishers.length == 0) {
            System.out.println("Нет доступных издательств. Сначала создайте издательство.");
            return null;
        }

        // Выбор издательства из существующих
        Publisher selectedPublisher = null;
        while (selectedPublisher == null) {
            System.out.println("Какому издательству принадлежит книга? Выберите номер из списка:");
            for (int i = 0; i < publishers.length; i++) {
                System.out.println((i + 1) + ". " + publishers[i]);
            }
            System.out.print("Ваш выбор: ");

            int choice = readInt(scanner);
            if (choice >= 1 && choice <= publishers.length) {
                selectedPublisher = publishers[choice - 1];
            } else {
                System.out.println("Ошибка: выберите номер от 1 до " + publishers.length + ".");
            }
        }

        // Ввод названия книги
        String title = "";
        while (title.trim().isEmpty()) {
            System.out.print("Введите название книги: ");
            title = scanner.nextLine();
            if (title.trim().isEmpty()) {
                System.out.println("Ошибка: название книги не может быть пустым.");
            }
        }

        // Ввод года издания
        int year = 0;
        while (year <= 0) {
            System.out.print("Введите год издания: ");
            year = readInt(scanner);
            if (year <= 0) {
                System.out.println("Ошибка: год должен быть положительным числом.");
            }
        }

        // Ввод авторов
        int authorsCount = -1;
        while (authorsCount < 0) {
            System.out.print("Введите количество авторов (0, если авторов нет): ");
            authorsCount = readInt(scanner);
            if (authorsCount < 0) {
                System.out.println("Ошибка: количество авторов не может быть отрицательным.");
            }
        }

        try {
            if (authorsCount == 0) {
                return new Book(title, year, selectedPublisher);
            } else if (authorsCount == 1) {
                System.out.print("Введите имя автора: ");
                String author = scanner.nextLine();
                return new Book(title, author, year, selectedPublisher);
            } else {
                String[] authors = new String[authorsCount];
                for (int i = 0; i < authorsCount; i++) {
                    System.out.print("Введите имя автора №" + (i + 1) + ": ");
                    authors[i] = scanner.nextLine();
                }
                return new Book(title, authors, year, selectedPublisher);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка при создании книги: " + e.getMessage());
            return null;
        }
    }


    private static int readInt(Scanner scanner) {
        while (true) {
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.print("Ошибка: введите корректное целое число: ");
                scanner.nextLine();
            }
        }
    }

    // Расширение массива издательств с сохранением старых данных
    private static Publisher[] addPublisher(Publisher[] array, Publisher newItem) {
        Publisher[] newArray = Arrays.copyOf(array, array.length + 1);
        newArray[newArray.length - 1] = newItem;
        return newArray;
    }

    // Расширение массива книг с сохранением старых данных
    private static Book[] addBook(Book[] array, Book newItem) {
        Book[] newArray = Arrays.copyOf(array, array.length + 1);
        newArray[newArray.length - 1] = newItem;
        return newArray;
    }
}