public class Participant {

    String name;
    String birthDate;
    String phone;
    String adress;

    private static int idNumber = 0;
    private int id = 0;
    private String city;
    int age = 0;
    boolean doubleDipUsed = false;
    boolean fiftyPercentageUsed = false;
    boolean didCompete = false;
    int prize = 0;
    int currentYear = 2022;
    static int statsOfLessThan30 = 0;
    static int statsOfGreaterThan30AndLessThan50 = 0;
    static int statsOfGreaterThan50 = 0;




    public Participant(String name, String birthDate, String phone, String adress) {
        this.name = name;
        this.birthDate = birthDate;
        this.phone = phone;
        this.adress = adress;
        this.age = currentYear - Integer.parseInt(this.birthDate.substring(this.birthDate.length() - 4));
        String[] addressArray = this.adress.split(";");
        this.city = addressArray[addressArray.length-2];
        this.id = idNumber;
        idNumber++;
    }

    public static int getStatsOfLessThan30() {
        return statsOfLessThan30;
    }

    public static void setStatsOfLessThan30(int statsOfLessThan30) {
        Participant.statsOfLessThan30 = statsOfLessThan30;
    }

    public static int getStatsOfGreaterThan30AndLessThan50() {
        return statsOfGreaterThan30AndLessThan50;
    }

    public static void setStatsOfGreaterThan30AndLessThan50(int statsOfGreaterThan30AndLessThan50) {
        Participant.statsOfGreaterThan30AndLessThan50 = statsOfGreaterThan30AndLessThan50;
    }

    public static int getStatsOfGreaterThan50() {
        return statsOfGreaterThan50;
    }

    public static void setStatsOfGreaterThan50(int statsOfGreaterThan50) {
        Participant.statsOfGreaterThan50 = statsOfGreaterThan50;
    }




    public void getAgeStats(){

        if(age <= 30){
            statsOfLessThan30++;
        }
        else if((age > 30) && (age <= 50) ){
            statsOfGreaterThan30AndLessThan50++;
        }
        else if(age >50){
            statsOfGreaterThan50++;

        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getDidCompete() {
        return didCompete;
    }

    public void setDidCompete(boolean didCompete) {
        this.didCompete = didCompete;
    }

    public int getPrize() {
        return prize;
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }

    public boolean isDoubleDipUsed() {
        return doubleDipUsed;
    }

    public void setDoubleDipUsed(boolean doubleDipUsed) {
        this.doubleDipUsed = doubleDipUsed;
    }

    public boolean isFiftyPercentageUsed() {
        return fiftyPercentageUsed;
    }

    public void setFiftyPercentageUsed(boolean fiftyPercentageUsed) {
        this.fiftyPercentageUsed = fiftyPercentageUsed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
