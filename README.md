# Proje Adı: E-Ticket

Online uçak ve otobüs bileti satış uygulaması.

## Servisler: 

### ETICKET-SERVICE-DISCOVERY: 
Farklı adreslerde bulunan servislerin adres bilgilerini bilmeye gerek kalmadan birbirleriyle iletişim kurabilmesini sağlar. Servisler arası iletişimi kolaylaştırır ve sadeleştirir.

### ETICKET-GW: 
Tüm projenin giriş kapısı görevini görür. AUTH-SERVICE’den aldığı token ile erişim durumlarını yönetir ve yönlendirme işlemleri yapar. 

### ETICKET-AUTH-SERVICE: 
Register ve Login işlemlerinin gerçekleştiği yerdir. PostgreSQL “userdb” veri tabanına bağlıdır. Register işlemi sonrası veri tabanına kullanıcı kaydetme işlemlerini yapar. Login işlemi sırasında veri tabanından kullanıcıyı çekerek kontrol işlemlerini yapar ve “Token” oluşturur. Başarılı kullanıcı kaydı işlemleri sonrası RabbitMQ aracılığıyla NOTIFICATION-SERVICE’e kullanıcı kayıt bilgisi mesajı gönderir. Log ve exception mesajlarını Kafka aracılığıyla LOG-SERVICE’e gönderir.

### ETICKET-SERVICE: 
Ticket ve Trip varlıklarını içerir. PostgreSQL “eticketdb” veri tabanına bağlıdır. Ticket ve Trip işlemlerini gerçekleştirir. PAYMENT-SERVICE’e feign client aracılığıyla senkron bir şekilde ödeme bilgilerini gönderir. PAYMENT-SERVICE’in, ödeme işlemini tamamladıktan sonra gerekli bilgileri geri gönderdiği RabbitMQ kuyruğunu dinler ve ödeme sonrası işlemlerini gerçekleştirir. RabbitMQ aracılığıyla NOTIFICATION-SERVICE’e alınan bilet bilgilerini içeren bir mesaj gönderir. Log ve exception mesajlarını Kafka aracılığıyla LOG-SERVICE’e gönderir. Her “trips” veri tabanı tablosu güncelleme işlemi sonrası kaydın kopyasını Kafka aracılığıyla INDEX-SERVICE’e gönderir.

### ETICKET-INDEX-SERVICE: 
“trips” veri tabanı tablosunun güncellendiği her durumda ETICKET-SERVICE’in Kafka aracılığıyla gönderdiği bilgileri Elasticsearch’e kaydetme işlemini gerçekleştirir.

### ETICKET-SEARCH-SERVICE: 
Elasticsearch’e bağlıdır. Kullanıcıların Trip verileri arasında arama, filtreleme ve sıralama işlemleri yapabilmelerini sağlar. Admin kullanıcıları bu işlemleri tüm Trip verileri arasında gerçekleştirebilirken bireysel ve kurumsal kullanıcılar sadece alınmaya müsait olan (tarihi geçmemiş, bilet sayısı tükenmemiş ve iptal edilmemiş) Trip verileri arasında gerçekleştirebilir.

### ETICKET-USER-SERVICE: 
Admin, Individual_User, Company_User, bunların ortak verilerini tutan “abstract” User varlıklarını ve Role varlığını içerir. PostgreSQL “userdb” veri tabanına bağlıdır. Kullanıcı ve rol işlemlerini gerçekleştirir. Log ve exception mesajlarını Kafka aracılığıyla LOG-SERVICE’e gönderir.

### LOG-SERVICE: 
Bütün servislerden Kafka aracılığıyla gönderilen log ve exception mesajlarını alır. Bu mesajları mongoDB “logdb” veri tabanındaki “exception-log-messages” ve “log-messages” koleksiyonlarına kaydeder.

### NOTIFICATION-SERVICE: 
MongoDB “notificationdb” veri tabanına bağlıdır. “emails”, “sms” ve “push_notifications” dokümanlarına sahiptir. RabbitMQ aracılığıyla AUTH-SERVICE ve ETICKET-SERVICE’den gönderilen mesajları “NotificationType” bilgisine göre (bir veya birden fazla olabilir) email, sms ya da push notification olarak gönderir, veri tabanındaki uygun koleksiyona kaydeder. RabbitMQ kuyruklarını dinleyerek mesaj göndermesi dışında kendi email gönderme endpoint’ine de sahiptir. Bu servise istek atılarak “email_template” oluşturulabilir, güncellenebilir, listelenebilir. Tercihe göre kayıtlı template ile metin girmeye gerek kalmadan email gönderilebilir ya da metin girilerek normal bir şekilde email gönderilebilir.  

### PAYMENT-SERVICE: 
Payment varlığını içerir. PostgreSQL “paymentdb” veri tabanına bağlıdır. Ödeme işlemlerini gerçekleştirir. ETICKET-SERVICE’den feign client aracılığıyla gelen ödeme bilgilerini alır ve ödeme işlemini gerçekleştirdikten sonra ödeme sonrası işlemlerin gerçekleşebileceğini bildirmek için RabbitMQ üzerinden gerekli bilgileri geri gönderir. Log ve exception mesajlarını Kafka aracılığıyla LOG-SERVICE’e gönderir.

---------

## Kullanılan teknolojiler:
- Kafka, RabbitMQ, PostgreSQL, MongoDB, Elasticsearch, Redis

## Projenin çalıştırılması:

1) Docker Desktop uygulamanız yok ise lütfen önce onun kurulumunu gerçekleştirin.

2) Terminalinizde “E-Ticket” klasörüne gelerek “docker-compose up -d” komutunu çalıştırın.  Bu komut ile proje için gerekli olan teknolojiler ve veri tabanları docker ile kurulmuş olacak. Komut sonrası Container’lar otomatik olarak ayağa kalkacaktır. 

3)  PostgreSQL ve MongoDB’de birkaç database oluşturmanız gerekmekte.

  PostgreSQL için; name: eticket, host name: localhost, port: 5433, usename: postgres 

  bilgileriyle server açtıktan sonra “eticketdb”, “paymentdb”, ve “userdb” veri tabanlarını eklemelisiniz.

  MongoDB için; “emaildb” adında bir veri tabanı oluşturup altına “email-templates” ve “emails” dokümanları,

  “logdb” adında bir veri tabanı oluşturup altına “exception-log-messages” ve “log-messages” dokümanları,

  “notificationdb” adında bir veri tabanı oluşturup altına “emails”, “push_notifications” ve “sms” dokümanları oluşturmalısınız.

4) Sonrasında ETICKET-SERVICE-DISCOVERY, ETICKET-GW ve ETICKET-AUTH-SERVICE servisleri ile başlayarak projeyi ayağa kaldırabilirsiniz.

------------

## İşlemler ve bazı bilgiler

### Kullanıcı veri tabanı tabloları

Üç çeşit kullanıcı bulunmaktadır: Admin, Individual_User, Company_User. Bu üç sınıfın ortak özellikleri (email, password, rol listesi…) abstract User sınıfında tutulmaktadır. “users” tablosu sadece ortak özellikleri içerir, diğer tablolar sadece kendi özelliklerini içerirler. 

### Rol veri tabanı tablosu

Bütün roller “roles” tablosunda tutulur. Kullanıcılar rol listesine sahiptir. Bu liste “user_roles” tablosunda tutulur.

### Kayıt ve giriş işlemleri

- Kayıt:

Admin, Company_User veya Individual_User olarak sisteme kayıt işlemi gerçekleştirilebilir. Kullanıcılar kayıt olurken varsayılan role atamaları yapılır. Önce, veri tabanına bakılarak rol daha önceden kaydedilmiş mi diye kontrol edilir. Eğer kaydedilmemişse önce veri tabanına kaydedilen rol daha sonra kullanıcıya atanır. 

Varsayılan roller:

Admin → ADMIN, USER

Individual User → INDIVIDUAL_USER, USER

Company User → COMPANY_USER, USER

Kayıt işlemi başarılı bir şekilde gerçekleştirildikten sonra kullanıcıya email/sms/anlık bildirim  gönderilir. Bu işlem RabbitMQ üzerinden asenkron bir şekilde gerçekleştirilir.

- Giriş:

Giriş işlemi tüm kullanıcılar için tek bir yerden gerçekleşir. Girilen bilgiler doğru ise bir “token” döner. O “token” ile kullanıcı kayıt olduğu rol için izin verilmiş olan işlemleri gerçekleştirebilir.

### Kullanıcı ve rol işlemleri:

Rol üzerinde yapılabilen tüm işlemler sadece Admin kullanıcısı tarafından yapılabilir.

Admin kullanıcıları tüm kullanıcı türlerini bir arada görüntüleyebilir, toplam kullanıcı sayısına ulaşabilir. Kullanıcıları “Admin”, “Individual User”, “Company User” şeklinde kategorize edilmiş olarak görüntüleyebilir, hesap aktiflik durumu ve hesap tipine göre listeleyebilir, hesap aktiflik durumlarını tek tek ya da toplu bir şekilde değiştirebilir. Kullanıcılara rol ekleyebilir, silebilir, güncelleyebilir ancak kayıt işlemi sırasında varsayılan olarak atanan roller üzerinde herhangi bir işlem yapamaz. Kendi profil bilgilerine ulaşabilir, profillerini güncelleyebilir ve şifrelerini değiştirebilirler. 

Kurumsal ve bireysel kullanıcılar kendi profil bilgilerine ulaşabilirler, profillerini güncelleyebilir ve şifrelerini değiştirebilirler.

### Trip ve Ticket işlemleri:

- Trip: Admin kullanıcıları “Trip” ekleyebilir, iptal edebilir, güncelleyebilir, durum  bilgisine göre listeleyebilir, genel Trip istatistiklerini ve tek bir Trip özelindeki istatistikleri gözlemleyebilirler. Bireysel ve kurumsal kullanıcılar sadece alınmaya müsait olan (tarihi geçmemiş, bilet sayısı tükenmemiş ve iptal edilmemiş) biletleri gözlemleyebilirler.

- Admin kullanıcısı Trip eklediğinde Trip kapasitesi kadar Ticket, koltuk numaralarıyla birlikte otomatik olarak üretilir. Trip kapasitesi uçak için 189, otobüs için 45 olarak belirlenmiştir.

- Tüm Ticket verilerinin getirildiği istekte Redis kullanılmıştır. Böylece Ticket verileri her istek atıldığında veri tabanından çekilmez, sadece yeni bir Trip eklenip Ticket oluşturan metod tetiklenince tekrar veri tabanından çekilerek veriler güncellenir. 

- Admin kullanıcısı tüm Ticket verilerini gözlemleyebilir, durum bilgisine göre (alınmaya müsait, tarihi geçmiş, satılmış) listeleyebilir. Bir Trip’e ait tüm Ticket verileri listeleyebilir, bir kullanıcının satın aldığı Ticket verilerine ulaşabilir ve genel Ticket istatistiklerini görüntüleyebilir. Ticket alma işlemini sadece bireysel ve kurumsal kullanıcılar gerçekleştirebilir. Bireysel ve kurumsal kullanıcılar bir Trip’e ait olan tüm alınmaya müsait Ticket verilerini listeleyebilir. Kendi almış oldukları Ticket verilerine ulaşabilirler.

### Ticket satın alma işlemi:

Bireysel ve kurumsal kullanıcılar tek ya da toplu bir şekilde bilet alabilirler.

- Tekli bilet alımı: Bu durumda kullanıcıdan ödeme tipi (CREDIT_CARD, EFT) ve alacağı biletin id değeri istenir. Ödeme işlemi için PAYMENT-SERVICE’e istek atılır, işlem başarıyla tamamlanırsa PAYMNET-SERVICE’ten gelen mesaj sonrası “Tickets” tablosu güncellenir ve yolcu email bilgisi tabloya eklenir. Kullanıcıya Trip ve Ticket bilgilerini içeren email/sms iletilir. (Ödeme işlemi senkron olarak gerçekleşir, ödeme sonrası verilerin kaydedilmesi ve kullanıcıya mesaj gönderme işlemleri asenkron olarak gerçekleşir.)

- Çoklu bilet alımı: Bu durumda kullanıcıdan, alacağı her bilet için yolcu adı, yolcu soyadı, yolcu cinsiyeti ve bilet id değeri istenir. Bilet id değeri aynı seyahate ait de olabilir farklı bir seyahate ait de olabilir. Çoklu bilet alımında ödemeyi yapan kişi hesap sahibi kullanıcıdır. Eğer kullanıcı kendisi için de bir bilet almak istiyorsa diğer yolculara yaptığı gibi kendi bilgilerini de girmelidir. Ödeme aşamasına geçilmeden önce projede istenilen gereksinimlerin kontrolü sağlanır. Herhangi bir sorun olmadığı durumda ödeme işlemi gerçekleşir ve alınan biletler veri tabanı tablosunda güncellenir, kullanıcıya bilgi mesajı iletilir.

### Arama işlemleri: 

Tüm kullanıcılar Trip veriler arasında arama, filtreleme ve sıralama işlemleri yapabilirler. 

Verileri aracın kalkış yapacağı güne, kalkış ve varış yerlerine, araç tipine (BUS, PLANE) göre filtreleyebilirler. PRICE verisine göre artan, azalan ya da varsayılan şekilde sıralayabilirler. Çıkan sonuçları sayfalarla gözlemleyebilirler.

Admin kullanıcıları arama işlemlerini iptal edilen seyahatler de dahil olmak üzere tüm seyahatler arasında yaparken bireysel ve kurumsal kullanıcılar sadece Ticket alınmaya müsait olan Trip verileri arasında yapabilir.

-------

## Projeden Bazı Görseller:

### Mikro-servis yapısı:


<img src="https://github.com/user-attachments/assets/3d18ab78-10de-4616-b93f-736bf0bf7d28" alt="" style="width:1000px;"/>

<br><br>
  
<img src="https://github.com/user-attachments/assets/df2748b8-e31f-4dac-90ea-d649be5eb254" alt="" style="width:500px;"/>

---------

### MongoDB - Bilet alındıktan sonra gönderilen mesaj

<img src="https://github.com/user-attachments/assets/3b7a4708-9173-430c-9bb1-5f5110ddcb49" alt="" style="width:700px;"/>

-----------

### Register - Login işlemleri

<img src="https://github.com/user-attachments/assets/b6759f10-17d6-4923-9ea3-5fe8ac1ca26e" alt="" style="width:700px;"/>

<br><br>

<img src="https://github.com/user-attachments/assets/59e20fd6-49de-4396-977b-7efa94411795" alt="" style="width:700px;"/>


----------

### Admin kullanıcısının erişebildiği istatistik bilgileri


<img src="https://github.com/user-attachments/assets/a67ec6e2-bd78-4f34-bc6e-b3915669e42f" alt="" style="width:500px;"/>

<br><br>

<img src="https://github.com/user-attachments/assets/01b2dbfc-1614-4c76-a826-386c4eca46ea" alt="" style="width:500px;"/>

<br><br>

<img src="https://github.com/user-attachments/assets/1da05c07-f791-4a74-b140-498ef2a5ad62" alt="" style="width:500px;"/>


----------

### Arama, filtreleme, sıralama işlemi


<img src="https://github.com/user-attachments/assets/4bacd589-cec1-4b9a-b9aa-cc0328f04c81" alt="" style="width:700px;"/>








