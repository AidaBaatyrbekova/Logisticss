package com.example.logistics.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service  // Бул класс Spring сервис катары катталат
public class OtpService {

    @Autowired  // Spring автоматтык түрдө JavaMailSender объектин берет
    private JavaMailSender mailSender;

    // Email менен байланышкан OTP маалыматтарын сактоо үчүн карта (thread-safe)
    private final Map<String, OtpData> otpStore = new ConcurrentHashMap<>();

    // OTP жарактуу болуучу убакыт (мүнөт менен)
    private static final int EXPIRE_MINUTES = 5;

    /**
     * Колдонуучунун emailине бир жолку сырсөз (OTP) жиберет.
     * Андан соң бул OTP'ни сактайт жана аны 5 мүнөт ичинде жарактуу кылат.
     *
     * @param email - Колдонуучунун электрондук почтасы
     */
    public void sendOtp(String email) {
        // 6 цифрадан турган коду генерациялайбыз
        String otp = generateOtp(6);

        // Генерацияланган OTP'ни сактайбыз
        saveOtp(email, otp);

        // Email билдирүүсүн түзүү
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);  // Кайсы почтага жөнөтүлөт
        message.setSubject("Сиздин OTP код");  // Email темасы
        message.setText("Сиздин бир жолку сырсөз (OTP): " + otp +
                "\nАл 5 мүнөт ичинде колдонулушу керек.");  // Email текст

        // Email жиберүү
        mailSender.send(message);
    }

    /**
     * Колдонуучунун берген OTP'си туурабы жана мөөнөтү өтпөйбү текшерет.
     *
     * @param email - Колдонуучунун почтасы
     * @param inputOtp - Колдонуучунун киргизген OTP коду
     * @return true эгер туура жана мөөнөтү бүтө элек болсо, башка учурда false
     */
    public boolean validateOtp(String email, String inputOtp) {
        // Эгер emailге байланыштуу OTP жок болсо, туура эмес деп эсептейбиз
        if (!otpStore.containsKey(email)) return false;

        // Сакталган OTP маалыматтарын алабыз
        OtpData data = otpStore.get(email);

        // Азыркы убакытты жана сакталган мөөнөттү салыштырабыз
        if (LocalDateTime.now().isAfter(data.getExpiryTime())) {
            // Эгер мөөнөтү өтүп кетсе, картадан тазалап салабыз
            otpStore.remove(email);
            return false;  // Мөөнөтү өткөн
        }

        // Колдонуучунун берген коду сакталган кодго барабарбы?
        boolean isValid = data.getOtp().equals(inputOtp);

        // Эгер туура болсо, картадан өчүрүп, бир жолу колдонулуучу кылып койобуз
        if (isValid) otpStore.remove(email);

        return isValid;
    }

    /**
     * Emailге байланыштуу OTP кодду жана анын жарактуулук мөөнөтүн сактайт.
     *
     * @param email - Колдонуучунун почтасы
     * @param otp - Генерацияланган OTP коду
     */
    private void saveOtp(String email, String otp) {
        // Азыркы убакыттан 5 мүнөт кошуп, expiration датасын түзөбүз
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(EXPIRE_MINUTES);

        // Emailди жана OTP + expiration датасын картага сактайбыз
        otpStore.put(email, new OtpData(otp, expiryTime));
    }

    /**
     * Берилген узундуктагы сандык OTP коду генерациялайт.
     *
     * @param length - OTP кодуна керектелген цифралардын саны
     * @return сандык түрдөгү OTP коду
     */
    private String generateOtp(int length) {
        String digits = "0123456789";  // Сандык цифралардын топтому
        StringBuilder otp = new StringBuilder();

        // length жолу кездемелүү сан тандап, OTP кодуна кошобуз
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * digits.length());
            otp.append(digits.charAt(index));
        }

        return otp.toString();  // Жыйынтык катары даяр сандык OTP код
    }

    /**
     * Ички класс, ар бир emailге байланыштуу OTP жана анын expiration убактысы.
     */
    private static class OtpData {
        private final String otp;  // Бир жолку сырсөз (код)
        private final LocalDateTime expiryTime;  // Жарактуулук мөөнөтү

        public OtpData(String otp, LocalDateTime expiryTime) {
            this.otp = otp;
            this.expiryTime = expiryTime;
        }

        public String getOtp() {
            return otp;
        }

        public LocalDateTime getExpiryTime() {
            return expiryTime;
        }
    }
}