//package modle.other;
//
//import conn.DB;
//import conn.NewHibernateUtil;
//import modle.tradelicens.TL_Approve;
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//import org.hibernate.criterion.Restrictions;
//import pojo.AplicationPayment;
//import pojo.Application;
//import pojo.Assessment;
//import pojo.SrShop;
//
//
//import java.sql.ResultSet;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Set;
//
///**
// * Created by Ranga on 2019-02-28.
// */
//public class FindTLArrias {
//
//
//    public static void main(String[] args) {
//        System.out.println("ela");
//
//
//        FindTLArrias findTLArrias = new FindTLArrias();
//
//        ArrayList<Arrias> tLbyAssessment = findTLArrias.getTLbyAssessment(8860, 0);
//        if (tLbyAssessment != null) {
//
//
//            if (tLbyAssessment.size() > 0) {
//                for(Arrias ar : tLbyAssessment){
//
//                    System.out.println(ar.isHasArrias());
//                    System.out.println(ar.getStatus());
//                    System.out.println(ar.getTradeName());
//                    System.out.println(ar.getCustomerName());
//                    System.out.println(ar.getArriasWithoutTax());
//
//
//
//                }
//            }
//        }else{
//            System.out.println("NULL");
//        }
//
//
//    }
//
//
//    public class Arrias {
//
//        private boolean hasArrias;
//        private String status;
//        private int idTLApplication;
//        private String tradeName;
//        private String customerName;
//        private double arriasWithoutTax;
//
//
//        public boolean isHasArrias() {
//            return hasArrias;
//        }
//
//        public void setHasArrias(boolean hasArrias) {
//            this.hasArrias = hasArrias;
//        }
//
//        public int getIdTLApplication() {
//            return idTLApplication;
//        }
//
//        public void setIdTLApplication(int idTLApplication) {
//            this.idTLApplication = idTLApplication;
//        }
//
//        public String getTradeName() {
//            return tradeName;
//        }
//
//        public void setTradeName(String tradeName) {
//            this.tradeName = tradeName;
//        }
//
//        public String getCustomerName() {
//            return customerName;
//        }
//
//        public void setCustomerName(String customerName) {
//            this.customerName = customerName;
//        }
//
//        public double getArriasWithoutTax() {
//            return arriasWithoutTax;
//        }
//
//        public void setArriasWithoutTax(double arriasWithoutTax) {
//            this.arriasWithoutTax = arriasWithoutTax;
//        }
//
//        public String getStatus() {
//            return status;
//        }
//
//        public void setStatus(String status) {
//            this.status = status;
//        }
//    }
//
//
//    public ArrayList<Arrias> getTLbyAssessment(int idAssess, int idShop) {
//
//        ArrayList<Arrias> arriasList = new ArrayList<>();
//
//        Session session = NewHibernateUtil.getSessionFactory().openSession();
//        session.beginTransaction().commit();
//        try {
//
//
//            if (idShop > 0) {
//                SrShop shop = (SrShop) session.get(SrShop.class, idShop);
//
//                List<Application> srShop = session.createCriteria(Application.class).add(Restrictions.eq("srShop", shop)).list();
//                if (srShop.size() > 0) {
//
//                    for (Application app : srShop) {
//                        if (app.getYear() == getCurrentYear()) {
//
//                            if (app.getStatues() == 1) {
//
//                                Arrias arrias = new Arrias();
//
//                                if (app.getApproveToPaymant() == 2) {
//                                    Set<AplicationPayment> aplicationPayments = app.getAplicationPayments();
//                                    if (aplicationPayments.size() > 0) {
//                                        for (AplicationPayment payment : aplicationPayments) {
//                                            if (payment.getPayment().getStatus() == 1) {
//                                                //   paid = true;
//                                                arrias.setHasArrias(false);
//                                                arrias.setStatus("Paid : Paid Fro This");
//                                                arrias.setIdTLApplication(app.getIdApplication());
//                                                arrias.setCustomerName(app.getCusName());
//                                                arrias.setTradeName(app.getTradeName());
//                                                arrias.setArriasWithoutTax(app.getTaxAmount());
//                                              //  arriasList.add(arrias);
//                                                break;
//                                            } else {
//                                                arrias.setHasArrias(true);
//                                                arrias.setStatus("NotPay : Still Not  Pay");
//                                                arrias.setIdTLApplication(app.getIdApplication());
//                                                arrias.setCustomerName(app.getCusName());
//                                                arrias.setTradeName(app.getTradeName());
//                                                arrias.setArriasWithoutTax(app.getTaxAmount());
//
//                                            }
//                                        }
//                                    } else {
//                                        arrias.setHasArrias(true);
//                                        arrias.setStatus("NotPay : Still Not  Pay");
//                                        arrias.setIdTLApplication(app.getIdApplication());
//                                        arrias.setCustomerName(app.getCusName());
//                                        arrias.setTradeName(app.getTradeName());
//                                        arrias.setArriasWithoutTax(app.getTaxAmount());
//                                       // arriasList.add(arrias);
//                                    }
//                                } else {
//                                    arrias.setHasArrias(false);
//                                    arrias.setStatus("Pending : Still Not Approve To Pay");
//                                    arrias.setIdTLApplication(app.getIdApplication());
//                                    arrias.setCustomerName(app.getCusName());
//                                    arrias.setTradeName(app.getTradeName());
//                                    arrias.setArriasWithoutTax(app.getTaxAmount());
//                                }
//
//                                arriasList.add(arrias);
//
//                            }
//                        }
//                    }
//                }
//
//            }
//
//            if (idAssess > 0) {
//                System.out.println("mekata awa");
//                Assessment ass = (Assessment) session.load(Assessment.class, idAssess);
//                if (ass != null) {
//                    List<Application> applications = session.createCriteria(Application.class)
//                            .add(Restrictions.eq("assessment", ass)).list();
//                    if (applications.size() > 0) {
//
//                        for (Application app : applications) {
//
//
//                            if (app.getYear() == getCurrentYear()) {
//
//                                if (app.getStatues() == 1) {
//
//                                    Arrias arrias = new Arrias();
//
//                                    if (app.getApproveToPaymant() == 2) {
//                                        Set<AplicationPayment> aplicationPayments = app.getAplicationPayments();
//                                        if (aplicationPayments.size() > 0) {
//                                            for (AplicationPayment payment : aplicationPayments) {
//                                                if (payment.getPayment().getStatus() == 1) {
//                                                    //   paid = true;
//                                                    arrias.setHasArrias(false);
//                                                    arrias.setStatus("Paid : Paid Fro This");
//                                                    arrias.setIdTLApplication(app.getIdApplication());
//                                                    arrias.setCustomerName(app.getCusName());
//                                                    arrias.setTradeName(app.getTradeName());
//                                                    arrias.setArriasWithoutTax(app.getTaxAmount());
//                                                    break;
//                                                } else {
//                                                    arrias.setHasArrias(true);
//                                                    arrias.setStatus("NotPay : Still Not  Pay");
//                                                    arrias.setIdTLApplication(app.getIdApplication());
//                                                    arrias.setCustomerName(app.getCusName());
//                                                    arrias.setTradeName(app.getTradeName());
//                                                    arrias.setArriasWithoutTax(app.getTaxAmount());
//                                                }
//                                            }
//                                        } else {
//                                            arrias.setHasArrias(true);
//                                            arrias.setStatus("NotPay : Still Not  Pay");
//                                            arrias.setIdTLApplication(app.getIdApplication());
//                                            arrias.setCustomerName(app.getCusName());
//                                            arrias.setTradeName(app.getTradeName());
//                                            arrias.setArriasWithoutTax(app.getTaxAmount());
//                                        }
//                                    } else {
//                                        arrias.setHasArrias(false);
//                                        arrias.setStatus("Pending : Still Not Approve To Pay");
//                                        arrias.setIdTLApplication(app.getIdApplication());
//                                        arrias.setCustomerName(app.getCusName());
//                                        arrias.setTradeName(app.getTradeName());
//                                        arrias.setArriasWithoutTax(app.getTaxAmount());
//                                    }
//
//                                    arriasList.add(arrias);
//
//                                }
//                            }
//                        }
//                    } else {
//                        //No Trade Licens
//                    }
//                }
//            }
//            return arriasList;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            session.close();
//        }
//
//    }
//
//
//    public int getCurrentMonth() {
//        int m = 0;
//        Date sd = getSystemDateByQuary();
//        if (sd != null) {
//            m = Integer.parseInt(new SimpleDateFormat("MM").format(sd));
//        }
//        //  System.out.println(m);
//        return m;
//    }
//
//    public int getCurrentDay() {
//        int d = 0;
//        Date sd = getSystemDateByQuary();
//        if (sd != null) {
//            d = Integer.parseInt(new SimpleDateFormat("dd").format(sd));
//        }
//        //  System.out.println(d);
//        return d;
//    }
//
//    public int getCurrentYear() {
//        int y = 0;
//        Date sd = getSystemDateByQuary();
//        if (sd != null) {
//            y = Integer.parseInt(new SimpleDateFormat("yyyy").format(sd));
//        }
//        // System.out.println(y);
//        return y;
//    }
//
//
//    public Date getSystemDateByQuary() {
//        String qq = "SELECT\n" +
//                "systemdate.idSystemDate,\n" +
//                "systemdate.systemDate,\n" +
//                "systemdate.systemDate_status,\n" +
//                "systemdate.change_user_id\n" +
//                "FROM `systemdate`\n" +
//                "WHERE\n" +
//                "systemdate.systemDate_status = 1";
//        try {
//            ResultSet data = DB.getData(qq);
//            if (data.last()) {
//                java.sql.Date systemDate = data.getDate("systemDate");
//                Date sd = systemDate;
//                return sd;
//            } else {
//                return null;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//
//}
