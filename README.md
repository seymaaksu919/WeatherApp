# 🌦️ Weather App — Hava Durumu Uygulaması

Kotlin ve Jetpack Compose kullanılarak geliştirilen bir Android hava durumu uygulaması.

Uygulama sayesinde şehir araması yapabilir, seçilen şehrin güncel hava durumunu, saatlik tahminlerini ve 7 günlük hava durumu tahminini görüntüleyebilirsin.

Hava durumu verileri [Open-Meteo API](https://open-meteo.com/) üzerinden alınmaktadır.

## ✨ Özellikler

- 🔍 Şehir adına göre arama yapma
- 🌡️ Güncel sıcaklık ve hava durumu bilgilerini görüntüleme
- 💧 Nem oranını görüntüleme
- 💨 Rüzgâr hızını görüntüleme
- 🕒 Saatlik hava durumu tahminlerini görüntüleme
- 📅 7 günlük hava durumu tahminini görüntüleme
- 🌤️ Hava durumu kodlarını açıklama ve emojilerle gösterme
- ⏳ Veri yüklenirken yüklenme durumunu gösterme
- ⚠️ Hata ve boş sonuç durumlarını yönetme
- 🔄 Hata durumunda yeniden deneme

## 🛠️ Kullanılan Teknolojiler

| Teknoloji | Kullanım amacı |
|---|---|
| Kotlin | Uygulamanın ana programlama dili |
| Jetpack Compose | Kullanıcı arayüzünü oluşturma |
| Material 3 | Arayüz bileşenleri ve tasarım |
| ViewModel | Ekran durumlarını yönetme |
| Kotlin Coroutines | Asenkron işlemleri yürütme |
| StateFlow | Ekran durumlarını gözlemleme |
| Retrofit | API isteklerini gerçekleştirme |
| Gson | JSON verilerini dönüştürme |
| Open-Meteo API | Şehir arama ve hava durumu verileri |
| JUnit | Otomatik testler |

## 🏗️ Proje Mimarisi

Proje; kullanıcı arayüzü, durum yönetimi, veri işlemleri ve API bağlantıları birbirinden ayrılacak şekilde yapılandırılmıştır.

```text
com.example.myapplication/
├── data/
│   ├── ApiClient.kt
│   ├── CityApi.kt
│   ├── ForecastApi.kt
│   ├── ForecastResponse.kt
│   ├── CityRepository.kt
│   └── WeatherRepository.kt
│
├── model/
│   ├── City.kt
│   ├── Forecast.kt
│   └── WeatherCondition.kt
│
├── ui/
│   ├── city/
│   ├── forecast/
│   ├── component/
│   ├── theme/
│   ├── HavaNavHost.kt
│   └── LoadState.kt
│
└── MainActivity.kt
```

Test dosyaları `app/src/test/` dizininde bulunmaktadır.

### 📂 Klasörlerin ve Dosyaların Görevleri

- **`data/`**: API istemcilerini, veri isteklerini ve Repository sınıflarını içerir.
- **`model/`**: Şehir ve hava durumu verilerinin modellerini içerir.
- **`ui/city/`**: Şehir arama ekranı ve ilgili durum yönetimi kodlarını içerir.
- **`ui/forecast/`**: Hava durumu tahmin ekranı ve ilgili durum yönetimi kodlarını içerir.
- **`ui/component/`**: Birden fazla ekranda kullanılabilen arayüz bileşenlerini içerir.
- **`ui/theme/`**: Uygulamanın renk, tema ve yazı tipi ayarlarını içerir.
- **`MainActivity.kt`**: Android uygulamasının başlangıç noktasıdır.

### 🔄 Uygulama Nasıl Çalışır?

1. Kullanıcı şehir arama ekranına bir şehir adı girer.
2. `CityViewModel`, arama işlemini başlatır.
3. `CityRepository`, `CityApi` aracılığıyla şehir bilgilerini alır.
4. Kullanıcı arama sonuçlarından bir şehir seçer.
5. Şehrin enlem ve boylam bilgileri kullanılarak hava durumu isteği yapılır.
6. `WeatherRepository`, gelen verileri uygulamanın modellerine dönüştürür.
7. `ForecastViewModel`, ekranın durumunu günceller.
8. `ForecastScreen`, güncel hava durumu ile saatlik ve 7 günlük tahminleri gösterir.

## 🌐 Kullanılan API'ler

### 1. Geocoding API

Şehir adına göre arama yapar ve ilgili şehrin koordinatlarını getirir.

[Open-Meteo Geocoding API](https://geocoding-api.open-meteo.com/)

### 2. Forecast API

Enlem ve boylam bilgilerini kullanarak güncel hava durumu, saatlik tahminler ve günlük tahminler sağlar.

[Open-Meteo Forecast API](https://api.open-meteo.com/)

Bu projede kullanılan Open-Meteo uç noktaları için API anahtarı gerekmemektedir.

## 🧪 Testler

Projedeki testler, belirli işlevlerin beklenen şekilde çalışıp çalışmadığını kontrol etmek için hazırlanmıştır.

- **`WeatherConditionTest.kt`**: Hava durumu kodlarının doğru açıklamalara dönüştürülmesini kontrol eder.
- **`CityApiTest.kt`**: Şehir API istemcisinin oluşturulmasını kontrol eder.
- **`ForecastApiTest.kt`**: Hava durumu API istemcisinin oluşturulmasını kontrol eder.
- **`CityViewModelTest.kt`**: Şehir arama durumlarının yönetimini test eder.
- **`ForecastViewModelTest.kt`**: Hata durumunun yönetimini test eder.

API istemcisi testleri gerçek internet bağlantısı kurmaz; bu nedenle API servisinin erişilebilir olduğunu tek başlarına doğrulamazlar.

## 🚀 Kurulum

Projeyi kendi bilgisayarında çalıştırmak için:

1. Bu GitHub deposunu klonla:

   ```bash
   git clone https://github.com/GITHUB_KULLANICI_ADIN/Weather-App.git
   ```

2. Projeyi Android Studio ile aç.
3. Gradle senkronizasyonunun tamamlanmasını bekle.
4. Uygulamayı bir Android emülatöründe veya uyumlu bir Android cihazda çalıştır.

**Not:** Şehir arama ve hava durumu verilerini alabilmek için internet bağlantısı gereklidir.


## 📊 Veri Kaynağı

Hava durumu verileri [Open-Meteo](https://open-meteo.com/) tarafından sağlanmaktadır.

## Uygulamaya ait Görseller

<img width="252" height="552" alt="image" src="https://github.com/user-attachments/assets/bf2f8354-d365-4277-ad7d-f335bd5229be" />

<img width="251" height="553" alt="image" src="https://github.com/user-attachments/assets/6d9df0f8-ce23-45d5-9c3f-139c5907aff1" />


<img width="241" height="565" alt="image" src="https://github.com/user-attachments/assets/ad1971bb-33a3-48c9-929f-92323362e2a5" />

<img width="246" height="552" alt="image" src="https://github.com/user-attachments/assets/67b77097-78f9-49d2-a715-48b0d1f2fcc2" />

<img width="655" height="302" alt="image" src="https://github.com/user-attachments/assets/c1edcb95-23c5-4a98-8c29-811f9007e6d1" />







---

**Kotlin ve Jetpack Compose ile geliştirilmiştir.** 💙
