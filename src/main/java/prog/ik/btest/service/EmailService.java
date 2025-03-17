package prog.ik.btest.service;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String body);
}
