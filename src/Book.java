public class Book {
    private int bid;
    private String title;
    private String author;
    private int pages;

    public Book(int bid,String title,String author,int pages) {
        this.bid = bid;
        this.title = title;
        this.author = author;
        this.pages = pages;
    }

    public int getBid() {return bid;}
    public String getTitle() {return title;}
    public String getAuthor() {return author;}
    public int getPages() {return pages;}

    @Override
    public String toString() {
        return "Book{" +
                "bid=" + bid +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", pages=" + pages +
                '}';
    }
}
