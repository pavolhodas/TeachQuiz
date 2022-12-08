package com.example.TeachQuiz.user;

import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender emailSenderService;
    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder, JavaMailSender emailSenderService, ModelMapper modelMapper) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.emailSenderService = emailSenderService;
        this.modelMapper = modelMapper;
    }

    @Override
    public String getAccessToken(String username) {
        User user = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user.getVerificationCode();
    }
    @Override
    public boolean verifyUser(String verificationCode) {
        User user = repository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            repository.save(user);

            return true;
        }
    }
    @Override
    public Optional<User> getUserByUsername(String username) {
        return this.repository.findByUsername(username);
    }

    @Override
    public User updateUser(User user) {
        return this.repository.save(user);
    }

    @Override
    public UserDTO addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);
        sendVerifyEmail(user);
        if(user.getRole() == null){
            user.setRole("USER");
        }

        return convertToDtoLogin(this.repository.save(user));
    }

    @Override
    public void changePassword(String email, String password) {
        User user = repository.getUserByEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        repository.save(user);
    }

    @Override
    public User getDisabledUser() {
        return repository.getDisabledUser();
    }

    @Override
    public void sendPasswordResetEmail(String email) {
        sendChangePasswordEmail(email);
    }

    private UserDTO convertToDtoLogin(User user){
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setPassword(user.getPassword());
        return userDTO;
    }

    public void sendChangePasswordEmail(String email){
        String toAddress = email;
        String fromAddress = "pavolhodas4@gmail.com";
        String senderName = "Quiz";
        String subject = "Zmena hesla";
        String content = "Zmente si heslo:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">ZMENIŤ HESLO</a></h3>"
                + "Ďakujeme,<br>"
                + "Guiz.";

        MimeMessage message = emailSenderService.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(fromAddress, senderName);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        try {
            helper.setTo(toAddress);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        try {
            helper.setSubject(subject);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        String passwordRecoveryURL = "http://localhost:4200" + "/changePassword/" + email;

        content = content.replace("[[URL]]", passwordRecoveryURL);

        try {
            helper.setText(content, true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        emailSenderService.send(message);

    }
    public void sendVerifyEmail( User user) {
        String toAddress = user.getEmail();
        String fromAddress = "pavolhodas4@gmail.com";
        String senderName = "Quiz";
        String subject = "Submit registration";
        String content = "<head>\n" +
                "  <meta charset=\"utf-8\">\n" +
                "  <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "  <style amp4email-boilerplate>\n" +
                "    body {\n" +
                "      visibility: hidden\n" +
                "    }\n" +
                "  </style>\n" +
                "\n" +
                "  <script async src=\"https://cdn.ampproject.org/v0.js\"></script>\n" +
                "\n" +
                "\n" +
                "  <style amp-custom>\n" +
                "    .u-row {\n" +
                "      display: flex;\n" +
                "      flex-wrap: nowrap;\n" +
                "      margin-left: 0;\n" +
                "      margin-right: 0;\n" +
                "    }\n" +
                "    \n" +
                "    .u-row .u-col {\n" +
                "      position: relative;\n" +
                "      width: 100%;\n" +
                "      padding-right: 0;\n" +
                "      padding-left: 0;\n" +
                "    }\n" +
                "    \n" +
                "    .u-row .u-col.u-col-100 {\n" +
                "      flex: 0 0 100%;\n" +
                "      max-width: 100%;\n" +
                "    }\n" +
                "    \n" +
                "    @media (max-width: 767px) {\n" +
                "      .u-row:not(.no-stack) {\n" +
                "        flex-wrap: wrap;\n" +
                "      }\n" +
                "      .u-row:not(.no-stack) .u-col {\n" +
                "        flex: 0 0 100%;\n" +
                "        max-width: 100%;\n" +
                "      }\n" +
                "    }\n" +
                "    \n" +
                "    body {\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "    }\n" +
                "    \n" +
                "    table,\n" +
                "    tr,\n" +
                "    td {\n" +
                "      vertical-align: top;\n" +
                "      border-collapse: collapse;\n" +
                "    }\n" +
                "    \n" +
                "    p {\n" +
                "      margin: 0;\n" +
                "    }\n" +
                "    \n" +
                "    .ie-container table,\n" +
                "    .mso-container table {\n" +
                "      table-layout: fixed;\n" +
                "    }\n" +
                "    \n" +
                "    * {\n" +
                "      line-height: inherit;\n" +
                "    }\n" +
                "    \n" +
                "    table,\n" +
                "    td {\n" +
                "      color: #000000;\n" +
                "    }\n" +
                "    \n" +
                "    a {\n" +
                "      color: #0000ee;\n" +
                "      text-decoration: underline;\n" +
                "    }\n" +
                "  </style>\n" +
                "\n" +
                "\n" +
                "</head>\n" +
                "\n" +
                "<body class=\"clean-body u_body\" style=\"margin: 0;padding: 0;background-color: #f9f9f9;color: #000000\">\n" +
                "  <!--[if IE]><div class=\"ie-container\"><![endif]-->\n" +
                "  <!--[if mso]><div class=\"mso-container\"><![endif]-->\n" +
                "  <table style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #f9f9f9;width:100%\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "    <tbody>\n" +
                "      <tr style=\"vertical-align: top\">\n" +
                "        <td style=\"word-break: break-word;border-collapse: collapse;vertical-align: top\">\n" +
                "          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #f9f9f9;\"><![endif]-->\n" +
                "\n" +
                "          <div style=\"padding: 0px;\">\n" +
                "            <div style=\"max-width: 600px;margin: 0 auto;background-color: #ffffff;\">\n" +
                "              <div class=\"u-row\">\n" +
                "\n" +
                "                <div class=\"u-col u-col-100\">\n" +
                "                  <div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\">\n" +
                "\n" +
                "                    <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                      <tbody>\n" +
                "                        <tr>\n" +
                "                          <td style=\"overflow-wrap:break-word;word-break:break-word;padding:20px;font-family:'Cabin',sans-serif;\" align=\"left\">\n" +
                "\n" +
                "                            <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "                              <tr>\n" +
                "                                <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\n" +
                "\n" +
                "<img src=\"https://cdn.discordapp.com/attachments/714885851267596360/986298715889369128/quiiz-logo2.png\">\n" +
                "                                </td>\n" +
                "                              </tr>\n" +
                "                            </table>\n" +
                "\n" +
                "                          </td>\n" +
                "                        </tr>\n" +
                "                      </tbody>\n" +
                "                    </table>\n" +
                "\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "\n" +
                "          <div style=\"padding: 0px;\">\n" +
                "            <div style=\"max-width: 600px;margin: 0 auto;background-color: #003399;\">\n" +
                "              <div class=\"u-row\">\n" +
                "\n" +
                "                <div class=\"u-col u-col-100\">\n" +
                "                  <div style=\"padding: 0px;background-color:#843fa1;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\">\n" +
                "\n" +
                "                    <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                      <tbody>\n" +
                "                        <tr>\n" +
                "                          <td style=\"overflow-wrap:break-word;word-break:break-word;padding:40px 10px 10px;font-family:'Cabin',sans-serif;\" align=\"left\">\n" +
                "\n" +
                "                            <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "                              <tr>\n" +
                "                                <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\n" +
                "\n" +
                "                                  <img alt=\"Image\" src=\"https://cdn.templates.unlayer.com/assets/1597218650916-xxxxc.png\" width=\"335\" height=\"43\" layout=\"intrinsic\" style=\"width: 26%;max-width: 26%;\">\n" +
                "\n" +
                "                                  </img>\n" +
                "                                </td>\n" +
                "                              </tr>\n" +
                "                            </table>\n" +
                "\n" +
                "                          </td>\n" +
                "                        </tr>\n" +
                "                      </tbody>\n" +
                "                    </table>\n" +
                "\n" +
                "                    <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                      <tbody>\n" +
                "                        <tr>\n" +
                "                          <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:'Cabin',sans-serif;\" align=\"left\">\n" +
                "\n" +
                "                            <div style=\"color: #ffffff; line-height: 140%; text-align: center; word-wrap: break-word;\">\n" +
                "                              <p style=\"font-size: 14px; line-height: 140%;\"><strong>T H A N K S&nbsp; &nbsp;F O R&nbsp; &nbsp;S I G N I N G&nbsp; &nbsp;U P !</strong></p>\n" +
                "                            </div>\n" +
                "\n" +
                "                          </td>\n" +
                "                        </tr>\n" +
                "                      </tbody>\n" +
                "                    </table>\n" +
                "\n" +
                "                    <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                      <tbody>\n" +
                "                        <tr>\n" +
                "                          <td style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 10px 31px;font-family:'Cabin',sans-serif;\" align=\"left\">\n" +
                "\n" +
                "                            <div style=\"color: #ffffff; line-height: 140%; text-align: center; word-wrap: break-word;\">\n" +
                "                              <p href=\"[[URL]]\" style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 28px; line-height: 39.2px;\"><strong><span style=\"line-height: 39.2px; font-size: 28px;\">Verify Your E-mail Address </span></strong>\n" +
                "                                </span>\n" +
                "                              </p>\n" +
                "                            </div>\n" +
                "\n" +
                "                          </td>\n" +
                "                        </tr>\n" +
                "                      </tbody>\n" +
                "                    </table>\n" +
                "\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "\n" +
                "          <div style=\"padding: 0px;\">\n" +
                "            <div style=\"max-width: 600px;margin: 0 auto;background-color: #ffffff;\">\n" +
                "              <div class=\"u-row\">\n" +
                "\n" +
                "                <div class=\"u-col u-col-100\">\n" +
                "                  <div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\">\n" +
                "\n" +
                "                    <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                      <tbody>\n" +
                "                        <tr>\n" +
                "                          <td style=\"overflow-wrap:break-word;word-break:break-word;padding:33px 55px;font-family:'Cabin',sans-serif;\" align=\"left\">\n" +
                "\n" +
                "                            <div style=\"line-height: 160%; text-align: center; word-wrap: break-word;\">\n" +
                "                              <p style=\"font-size: 14px; line-height: 160%;\"><span style=\"font-size: 22px; line-height: 35.2px;\">Hi, [[name]]</span></p>\n" +
                "                              <p style=\"font-size: 14px; line-height: 160%;\"><span style=\"font-size: 18px; line-height: 28.8px;\">You're almost ready to get started. Plese click on the button below to verify your email address and enjoy Quiiz with us! </span></p>\n" +
                "                            </div>\n" +
                "\n" +
                "                          </td>\n" +
                "                        </tr>\n" +
                "                      </tbody>\n" +
                "                    </table>\n" +
                "\n" +
                "                    <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                      <tbody>\n" +
                "                        <tr>\n" +
                "                          <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:'Cabin',sans-serif;\" align=\"left\">\n" +
                "\n" +
                "                            <div align=\"center\">\n" +
                "                              <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-spacing: 0; border-collapse: collapse;  font-family:'Cabin',sans-serif;\"><tr><td style=\"font-family:'Cabin',sans-serif;\" align=\"center\"><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" style=\"height:46px; v-text-anchor:middle; width:235px;\" arcsize=\"8.5%\" stroke=\"f\" fillcolor=\"#843fa1\"><w:anchorlock/><center style=\"color:#FFFFFF;font-family:'Cabin',sans-serif;\"><![endif]-->\n" +
                "                              <a href=\"[[URL]]\" target=\"_blank\" style=\"box-sizing: border-box;display: inline-block;font-family:'Cabin',sans-serif;text-decoration: none;text-align: center;color: #FFFFFF; background-color: #843fa1; border-radius: 4px;  width:auto; max-width:100%; overflow-wrap: break-word; word-break: break-word; word-wrap:break-word; \">\n" +
                "                                <span style=\"display:block;padding:14px 44px 13px;line-height:120%;\"><span style=\"font-size: 16px; line-height: 19.2px;\"><strong><span style=\"line-height: 19.2px; font-size: 16px;\">VERIFY YOUR EMAIL</span></strong>\n" +
                "                                </span>\n" +
                "                                </span>\n" +
                "                              </a>\n" +
                "                              <!--[if mso]></center></v:roundrect></td></tr></table><![endif]-->\n" +
                "                            </div>\n" +
                "\n" +
                "                          </td>\n" +
                "                        </tr>\n" +
                "                      </tbody>\n" +
                "                    </table>\n" +
                "\n" +
                "                    <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                      <tbody>\n" +
                "                        <tr>\n" +
                "                          <td style=\"overflow-wrap:break-word;word-break:break-word;padding:33px 55px 60px;font-family:'Cabin',sans-serif;\" align=\"left\">\n" +
                "\n" +
                "                            <div style=\"line-height: 160%; text-align: center; word-wrap: break-word;\">\n" +
                "                              <p style=\"line-height: 160%; font-size: 14px;\"><span style=\"font-size: 18px; line-height: 28.8px;\">Thanks,</span></p>\n" +
                "                              <p style=\"line-height: 160%; font-size: 14px;\"><span style=\"font-size: 18px; line-height: 28.8px;\">Quiiz</span></p>\n" +
                "                            </div>\n" +
                "\n" +
                "                          </td>\n" +
                "                        </tr>\n" +
                "                      </tbody>\n" +
                "                    </table>\n" +
                "\n" +
                "                  </div>\n" +
                "                </div>\n" +
                "\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "\n" +
                "          <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </tbody>\n" +
                "  </table>\n" +
                "<table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" background-color=\"white\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:41px 55px 18px;font-family:'Cabin',sans-serif;\" align=\"left\">\n" +
                "\n" +
                "                              <div style=\"color: #003399; line-height: 160%; text-align: center; word-wrap: break-word;\">\n" +
                "                                <p style=\"font-size: 14px; line-height: 160%;\"><span style=\"font-size: 20px; line-height: 32px;\"><strong>Get in touch</strong></span></p>\n" +
                "                                <p style=\"font-size: 14px; line-height: 160%;\"><span style=\"font-size: 16px; line-height: 25.6px; color: #000000;\">+11 111 333 4444</span></p>\n" +
                "                                <p style=\"font-size: 14px; line-height: 160%;\"><span style=\"font-size: 16px; line-height: 25.6px; color: #000000;\">pavolhodas4@gmail.com</span></p>\n" +
                "                              </div>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "\n" +
                "                      <table style=\"font-family:'Cabin',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 10px 33px;font-family:'Cabin',sans-serif;\" align=\"left\">\n" +
                "\n" +
                "                              <div align=\"center\">\n" +
                "                                <div style=\"display: table; max-width:244px;\">\n" +
                "                                  <!--[if (mso)|(IE)]><table width=\"244\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"border-collapse:collapse;\" align=\"center\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse; mso-table-lspace: 0pt;mso-table-rspace: 0pt; width:244px;\"><tr><![endif]-->\n" +
                "\n" +
                "\n" +
                "                                  <!--[if (mso)|(IE)]><td width=\"32\" style=\"width:32px; padding-right: 17px;\" valign=\"top\"><![endif]-->\n" +
                "                                  <table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"32\" height=\"32\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;margin-right: 17px\">\n" +
                "                                    <tbody>\n" +
                "                                      <tr style=\"vertical-align: top\">\n" +
                "                                        <td align=\"left\" valign=\"middle\" style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\n" +
                "                                          <a href=\"https://facebook.com/\" title=\"Facebook\" target=\"_blank\">\n" +
                "                                            <img src=\"https://cdn.tools.unlayer.com/social/icons/circle-black/facebook.png\" alt=\"Facebook\" title=\"Facebook\" width=\"32\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: none;height: auto;float: none;max-width: 32px !important\">\n" +
                "                                          </a>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </tbody>\n" +
                "                                  </table>\n" +
                "                                  <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "\n" +
                "                                  <!--[if (mso)|(IE)]><td width=\"32\" style=\"width:32px; padding-right: 17px;\" valign=\"top\"><![endif]-->\n" +
                "                                  <table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"32\" height=\"32\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;margin-right: 17px\">\n" +
                "                                    <tbody>\n" +
                "                                      <tr style=\"vertical-align: top\">\n" +
                "                                        <td align=\"left\" valign=\"middle\" style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\n" +
                "                                          <a href=\"https://linkedin.com/\" title=\"LinkedIn\" target=\"_blank\">\n" +
                "                                            <img src=\"https://cdn.tools.unlayer.com/social/icons/circle-black/linkedin.png\" alt=\"LinkedIn\" title=\"LinkedIn\" width=\"32\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: none;height: auto;float: none;max-width: 32px !important\">\n" +
                "                                          </a>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </tbody>\n" +
                "                                  </table>\n" +
                "                                  <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "\n" +
                "                                  <!--[if (mso)|(IE)]><td width=\"32\" style=\"width:32px; padding-right: 17px;\" valign=\"top\"><![endif]-->\n" +
                "                                  <table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"32\" height=\"32\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;margin-right: 17px\">\n" +
                "                                    <tbody>\n" +
                "                                      <tr style=\"vertical-align: top\">\n" +
                "                                        <td align=\"left\" valign=\"middle\" style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\n" +
                "                                          <a href=\"https://instagram.com/\" title=\"Instagram\" target=\"_blank\">\n" +
                "                                            <img src=\"https://cdn.tools.unlayer.com/social/icons/circle-black/instagram.png\" alt=\"Instagram\" title=\"Instagram\" width=\"32\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: none;height: auto;float: none;max-width: 32px !important\">\n" +
                "                                          </a>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </tbody>\n" +
                "                                  </table>\n" +
                "                                  <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "\n" +
                "                                  <!--[if (mso)|(IE)]><td width=\"32\" style=\"width:32px; padding-right: 17px;\" valign=\"top\"><![endif]-->\n" +
                "                                  <table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"32\" height=\"32\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;margin-right: 17px\">\n" +
                "                                    <tbody>\n" +
                "                                      <tr style=\"vertical-align: top\">\n" +
                "                                        <td align=\"left\" valign=\"middle\" style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\n" +
                "                                          <a href=\"https://youtube.com/\" title=\"YouTube\" target=\"_blank\">\n" +
                "                                            <img src=\"https://cdn.tools.unlayer.com/social/icons/circle-black/youtube.png\" alt=\"YouTube\" title=\"YouTube\" width=\"32\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: none;height: auto;float: none;max-width: 32px !important\">\n" +
                "                                          </a>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </tbody>\n" +
                "                                  </table>\n" +
                "                                  <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "\n" +
                "                                  <!--[if (mso)|(IE)]><td width=\"32\" style=\"width:32px; padding-right: 0px;\" valign=\"top\"><![endif]-->\n" +
                "                                  <table align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"32\" height=\"32\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;margin-right: 0px\">\n" +
                "                                    <tbody>\n" +
                "                                      <tr style=\"vertical-align: top\">\n" +
                "                                        <td align=\"left\" valign=\"middle\" style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\n" +
                "                                          <a href=\"https://email.com/\" title=\"Email\" target=\"_blank\">\n" +
                "                                            <img src=\"https://cdn.tools.unlayer.com/social/icons/circle-black/email.png\" alt=\"Email\" title=\"Email\" width=\"32\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: none;height: auto;float: none;max-width: 32px !important\">\n" +
                "                                          </a>\n" +
                "                                        </td>\n" +
                "                                      </tr>\n" +
                "                                    </tbody>\n" +
                "                                  </table>\n" +
                "                                  <!--[if (mso)|(IE)]></td><![endif]-->\n" +
                "\n" +
                "\n" +
                "                                  <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                "                                </div>\n" +
                "                              </div>\n" +
                "\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "  <!--[if mso]></div><![endif]-->\n" +
                "  <!--[if IE]></div><![endif]-->\n" +
                "</body>\n";

        content = content.replace("[[name]]", user.getUsername());

        String verifyURL = "http://localhost:4200" + "/verify/" + user.getVerificationCode();

        String verifyURL_QUIZ = "src/main/resources/quiiz-logo2.png";
        URL image = UserController.class.getResource("quiiz-logo2.png");
        content = content.replace("[[URL]]", verifyURL);
        content = content.replace("[[URL_QUIZ]]", verifyURL_QUIZ);

        String userName = user.getUsername();
        MimeMessage message = emailSenderService.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.setText(content, true);
            emailSenderService.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        emailSenderService.send(message);
    }

}