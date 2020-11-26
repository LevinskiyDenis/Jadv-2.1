public class Restaurant {

    int visitorsWantToOrder;
    int ordersCooked;
    int ordersDone;

    final int VISITORSTIMETOCHOOSE = 5000;
    final int TIMETOCOOK = 7000;
    final int VISITORSTIMETOEAT = 3000;

    public synchronized void bringFood() {

        System.out.println(Thread.currentThread().getName() + " на работе");

        while (ordersDone < 5) {

            while (visitorsWantToOrder == 0) {
                try {
                    if (ordersDone == 5) return;
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(Thread.currentThread().getName() + " принял заказ");
            visitorsWantToOrder -= 1;

            try {
                System.out.println("Заказ готовится...");
                Thread.sleep(TIMETOCOOK);
                ordersCooked += 1;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " принес заказ");
            ordersDone += 1;

            notifyAll();
        }
        System.out.println("Ресторан закрывается...");
    }


    public synchronized void orderFood() {

        if (ordersDone >= 5) {
            System.out.println(Thread.currentThread().getName() + ", ресторан закрыт");
            return;
        }

        System.out.println(Thread.currentThread().getName() + " зашел в ресторан");

        try {
            Thread.sleep(VISITORSTIMETOCHOOSE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        visitorsWantToOrder += 1;
        System.out.println(Thread.currentThread().getName() + " готов заказать");
        notifyAll();

        while (ordersCooked == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ordersCooked -= 1;
        System.out.println(Thread.currentThread().getName() + " ест заказ");
        try {
            Thread.sleep(VISITORSTIMETOEAT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " уходит");
        notifyAll();
    }
}
