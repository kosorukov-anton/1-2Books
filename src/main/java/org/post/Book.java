package org.post;

import java.util.Arrays;

public class Book {
    private String title;
    private Publisher publisher;
    private int year;
    private String[] authors;

    public Book(String title, int year, Publisher publisher) {
        setTitle(title);
        setYear(year);
        setPublisher(publisher);
        this.authors = null;
    }

    public Book(String title, String author, int year, Publisher publisher) {
        this(title, year, publisher);
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя автора не может быть пустым");
        }
        this.authors = new String[]{author};
    }

    public Book(String title, String[] authors, int year, Publisher publisher) {
        this(title, year, publisher);
        setAuthors(authors);
    }

    //*****************************************
    public String getTitle() {return title;}
    public Publisher getPublisher() {return publisher;}
    public int getYear() {return year;}
    public String[] getAuthors() {return authors;}

    public int getAuthorsCount() {return authors == null ? 0 : authors.length;}
    public String getAuthor(int index) {
        if (authors == null || index < 0 || index >= authors.length) {
            throw new IndexOutOfBoundsException("Некорректный индекс автора: " + index);
        }
        return authors[index];
    }


    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Название книги не может быть пустым");
        }
        this.title = title;
    }

    public void setPublisher(Publisher publisher) {
        if (publisher == null) {
            throw new IllegalArgumentException("Издательство не может быть null");
        }
        this.publisher = publisher;
    }

    public void setYear(int year) {
        if (year <= 0) {
            throw new IllegalArgumentException("Год издания должен быть положительным числом");
        }
        this.year = year;
    }

    public void setAuthors(String[] authors) {
        if (authors == null) {
            throw new IllegalArgumentException("Массив авторов не может быть null");
        }
        for (String author : authors) {
            if (author == null || author.trim().isEmpty()) {
                throw new IllegalArgumentException("Имя автора в массиве не может быть пустым");
            }
        }
        this.authors = authors;
    }

    public void setAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя автора не может быть пустым");
        }
        if (this.authors == null) {
            this.authors = new String[]{author};
        } else {
            this.authors = Arrays.copyOf(this.authors, this.authors.length + 1);
            this.authors[this.authors.length - 1] = author;
        }
    }

    public void print() {System.out.println(this);}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Название: ").append(title).append("\n");

        if (authors != null && authors.length > 0) {
            sb.append("Автор(ы): ").append(String.join(", ", authors)).append("\n");
        }

        sb.append("Издательство: ").append(publisher).append("\n");
        sb.append("Год: ").append(year);

        return sb.toString();
    }
}