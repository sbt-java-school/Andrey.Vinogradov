###Реализация кэширующего прокси
Сделана аннотация Cache для методов в рантайме.

Сделан набор прокси CacheType через Enum.

CacheHandler - основной интерфейс кэша. Он имплементирован в классах

- DefaultCacheHandler - обычный кэш, без ограничений
- SizeCacheHandler - кэш с ограничением на размер хранения - не используемые значения удаляются, если при вставке нового превышен заданный лимит. При установке нового лимита происходит очистка (если необходимо)
- TimeCacheHandler - кэш с ограничением на время хранения - значения, которые не используются достаточно долго удаляются отдельным демоном

Долго мучался с разными структурами для Size и Time кэшей, оказалось, что LinkedHashMap отлично подходит и для того, и для другого