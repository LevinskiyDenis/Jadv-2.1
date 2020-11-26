public class Main {

    public static void main(String[] args) {

        Restaurant restaurant = new Restaurant();
        Runnable wantToEat = restaurant::orderFood;
        Runnable work = restaurant::bringFood;

        new Thread(null, work, "Официант 1").start();
        new Thread(null, work, "Официант 2").start();
        new Thread(null, work, "Официант 3").start();

        for (int i = 0; i < 6; i++) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Thread(null, wantToEat, "Посетитель " + (i + 1)).start();
        }
    }
}
