###Формирование списков грузовиков.
Из TruckDaoMemoryImpl получается лист, из него при обходе формируется 2 мапы:
- truckByID - TreeMap\<Long, Truck\>
- truckByType - HashMap\<String, List\<Truck\>\>