package com.exhibit;


import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
     /*   ConnectionManager manager = TestConnectionManager.getInstance();
        TestDB testDB = new TestDB(manager);
        testDB.createTestDatabase();
*/
        for (int i = 0; i < 100; i++) {
            int hall1 = (int) (Math.random() * (8 - 2));
            System.out.println(hall1);
        }

        int hall1 = (int) (Math.random() * (8 - 2));



      /*  HallService hallService = ServiceFactory.getInstance().getHallService(BasicConnectionManager.getInstance());
        ExhibitionService exhibitionService = ServiceFactory.getInstance().getExhibitionService(BasicConnectionManager.getInstance());
        Optional<Exhibition> exhibition =  exhibitionService.findById(8);
        System.out.println(exhibition);

        List<Hall> hallList = hallService.findAll();
        System.out.println(PasswordHashing.toMD5("111111"));
        System.out.println(PasswordHashing.toMD5("222222"));
        System.out.println(PasswordHashing.toMD5("333333"));



        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = sdf.parse("2022-09-01");
            Date date2 = sdf.parse("2022-09-03");
            System.out.println(exhibitionService.inPast(exhibition.get().getId()));

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

       */
    }
}