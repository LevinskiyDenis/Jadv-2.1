public class Restaurant {

    int visitorsWantToOrder = 0;
    int ordersCooked = 0;

    int totalNumberOfVisitorsEntered = 0;
    int totalNumberOfOrdersAccepted = 0;
    int totalNumberOfOrdersDone = 0;

    final int VISITORSTIMETOCHOOSE = 5000;
    final int TIMETOCOOK = 7000;
    final int VISITORSTIMETOEAT = 3000;
    final int LIMIT = 5;

    public void bringFood() {

        System.out.println(Thread.currentThread().getName() + " на работе");

        while (totalNumberOfOrdersDone < LIMIT) {

            synchronized (this) {
                while (visitorsWantToOrder == 0) {

                    if (totalNumberOfOrdersAccepted >= LIMIT) {
                        System.out.println(Thread.currentThread().getName() + " закончил работу");
                        return;
                    }

                    try {
                        System.out.println(Thread.currentThread().getName() + " ждет заказов посетителей");
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println(Thread.currentThread().getName() + " принял заказ");
                visitorsWantToOrder--;
                totalNumberOfOrdersAccepted++;
            }

            System.out.println("Заказ готовится...");
            try {
                Thread.sleep(TIMETOCOOK);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (this) {
                ordersCooked++;
                System.out.println(Thread.currentThread().getName() + " принес заказ");
                totalNumberOfOrdersDone++;
                notifyAll();
            }
        }

        System.out.println(Thread.currentThread().getName() + " закончил работу");
    }


    public void orderFood() {

        if (totalNumberOfVisitorsEntered >= LIMIT) {
            System.out.println(Thread.currentThread().getName() + ", ресторан закрыт");
            return;
        }

        System.out.println(Thread.currentThread().getName() + " зашел в ресторан");
        totalNumberOfVisitorsEntered++;

        try {
            Thread.sleep(VISITORSTIMETOCHOOSE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this) {
            visitorsWantToOrder++;
            System.out.println(Thread.currentThread().getName() + " готов заказать");
            notifyAll();
        }

        synchronized (this) {
            while (ordersCooked == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ordersCooked--;
        }

        System.out.println(Thread.currentThread().getName() + " ест заказ");

        try {
            Thread.sleep(VISITORSTIMETOEAT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " уходит. Чао!");
    }
}
