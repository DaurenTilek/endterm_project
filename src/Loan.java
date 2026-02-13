public class Loan {
    private int sid;
    private int bid;
    private String loan_date;
    private String return_date;

    public Loan(int sid,int bid,String loan_date,String return_date) {
        this.sid = sid;
        this.bid = bid;
        this.loan_date = loan_date;
        this.return_date = return_date;
    }

    public int getSid() {return sid;}
    public int getBid() {return bid;}
    public String getLoan_date() {return loan_date;}
    public String getReturn_date() {return return_date;}

    @Override
    public String toString() {
        return "Loan{" +
                "sid=" + sid +
                ", bid=" + bid +
                ", loan_date='" + loan_date + '\'' +
                ", return_date='" + return_date + '\'' +
                '}';
    }
}
