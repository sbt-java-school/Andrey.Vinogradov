###Формирование списков грузовиков.

Задача: нужно реализовать DAO объект, возвращающий различные варианты списка грузовиков

Из TruckDaoMemoryImpl получается лист, из него при обходе формируется 2 мапы:
- truckByID - TreeMap\<Long, Truck\>
- truckByType - HashMap\<String, List\<Truck\>\>