package modle.asses;

import conn.DB;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import jdk.nashorn.internal.codegen.Compiler;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import pojo.AssCreditdebit;
import pojo.AssQstart;
import pojo.Assessment;

/**
 * @author Ranga
 */
public class GetArrias {


    public static void main(String[] args) {
        GetArrias arrias = new GetArrias();
        AllArrias ar = arrias.getAllArriasGetAllWarrant(600);

        if (ar != null) {
            System.out.println(ar.HaveArrias());
            System.out.println(ar.getTotalArrias());
            System.out.println(ar.getTotalWarrant());
            System.out.println(ar.getCd());
        } else {
            System.out.println("NULL");
        }

    }


    public AllArrias getAllArriasGetAllWarrant(int idAsssessmetn) {
        System.out.println(idAsssessmetn);
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().commit();

        AllArrias allArrias = new AllArrias();

        try {
            Assessment as = (Assessment) session.load(Assessment.class, idAsssessmetn);
            Integer assessmentSyn = as.getAssessmentSyn();

            List<pojo.AssCreditdebit> list = session.createCriteria(AssCreditdebit.class)
                    .add(Restrictions.eq("assessment", as))
                    .add(Restrictions.eq("assCreditDebitStatus", 1)).list();

            for (AssCreditdebit c : list) {
                Double assBalance = c.getAssBalance();
                allArrias.cd += assBalance;
            }


            if (as != null) {

                if (assessmentSyn == 0) {

                    Set<AssQstart> assQstarts = as.getAssQstarts();
                    for (AssQstart assQstart : assQstarts) {
                        Integer assQstartYear = assQstart.getAssQstartYear();
                        if (getCurrentYear() == assQstartYear) {
                            Integer qno = assQstart.getAssQstartQuaterNumber();
                            if (qno == 1) {
                                if (assQstart.getAssQstartStatus() == 1) {
                                    allArrias.setLya(assQstart.getAssQstartLycArreas());
                                    allArrias.setLyw(assQstart.getAssQstartLycWarrant());
                                }
                            } else if (qno == 2) {
                                if (assQstart.getAssQstartStatus() == 1) {
                                    allArrias.setLya(assQstart.getAssQstartLycArreas());
                                    allArrias.setLyw(assQstart.getAssQstartLycWarrant());
                                }
                                allArrias.setQ1a(assQstart.getAssQstartLqArreas());
                                allArrias.setQ1w(assQstart.getAssQstartLqWarrant());
                            } else if (qno == 3) {
                                if (assQstart.getAssQstartStatus() == 1) {
                                    allArrias.setLya(assQstart.getAssQstartLycArreas());
                                    allArrias.setLyw(assQstart.getAssQstartLycWarrant());
                                }
                                allArrias.setQ2a(assQstart.getAssQstartLqArreas());
                                allArrias.setQ2w(assQstart.getAssQstartLqWarrant());
                            } else if (qno == 4) {
                                if (assQstart.getAssQstartStatus() == 1) {
                                    allArrias.setLya(assQstart.getAssQstartLycArreas());
                                    allArrias.setLyw(assQstart.getAssQstartLycWarrant());
                                }
                                allArrias.setQ3a(assQstart.getAssQstartLqArreas());
                                allArrias.setQ3w(assQstart.getAssQstartLqWarrant());
                            }
                        }
                    }
                    return allArrias;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }

    }

    public int getPrviasQuater() {
        int currentQuater = getCurrentQuater();
        int pq = 0;
        if (currentQuater > 0 && currentQuater <= 4) {
            pq = currentQuater - 1;
            if (pq == 0) {
                pq = 4;
            }
        }
        return pq;
    }

    public int getCurrentQuater() {
        int q = 0;
        Date sd = getSystemDateByQuary();
        if (sd != null) {
            int month = Integer.parseInt(new SimpleDateFormat("MM").format(sd));
            if (month <= 3) {
                q = 1;
            } else if (month <= 6) {
                q = 2;
            } else if (month <= 9) {
                q = 3;
            } else if (month <= 12) {
                q = 4;
            }
        }
        //    System.out.println(q);
        return q;
    }

    public Date getSystemDateByQuary() {
        String qq = "SELECT\n"
                + "systemdate.idSystemDate,\n"
                + "systemdate.systemDate,\n"
                + "systemdate.systemDate_status,\n"
                + "systemdate.change_user_id\n"
                + "FROM `systemdate`\n"
                + "WHERE\n"
                + "systemdate.systemDate_status = 1";
        try {
            ResultSet data = DB.getData(qq);
            if (data.last()) {
                java.sql.Date systemDate = data.getDate("systemDate");
                Date sd = systemDate;
                return sd;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getCurrentYear() {
        int y = 0;
        Date sd = getSystemDateByQuary();
        if (sd != null) {
            y = Integer.parseInt(new SimpleDateFormat("yyyy").format(sd));
        }
        // System.out.println(y);
        return y;
    }

    public class AllArrias {

        /**
         * @return the lya
         */
        public double getLya() {
            return lya;
        }

        /**
         * @param lya the lya to set
         */
        public void setLya(double lya) {
            this.lya = lya;
        }

        /**
         * @return the lyw
         */
        public double getLyw() {
            return lyw;
        }

        /**
         * @param lyw the lyw to set
         */
        public void setLyw(double lyw) {
            this.lyw = lyw;
        }

        /**
         * @return the q1a
         */
        public double getQ1a() {
            return q1a;
        }

        /**
         * @param q1a the q1a to set
         */
        public void setQ1a(double q1a) {
            this.q1a = q1a;
        }

        /**
         * @return the q2a
         */
        public double getQ2a() {
            return q2a;
        }

        /**
         * @param q2a the q2a to set
         */
        public void setQ2a(double q2a) {
            this.q2a = q2a;
        }

        /**
         * @return the q3a
         */
        public double getQ3a() {
            return q3a;
        }

        /**
         * @param q3a the q3a to set
         */
        public void setQ3a(double q3a) {
            this.q3a = q3a;
        }

        /**
         * @return the q4a
         */
        public double getQ4a() {
            return q4a;
        }

        /**
         * @param q4a the q4a to set
         */
        public void setQ4a(double q4a) {
            this.q4a = q4a;
        }

        /**
         * @return the q1w
         */
        public double getQ1w() {
            return q1w;
        }

        /**
         * @param q1w the q1w to set
         */
        public void setQ1w(double q1w) {
            this.q1w = q1w;
        }

        /**
         * @return the q2w
         */
        public double getQ2w() {
            return q2w;
        }

        /**
         * @param q2w the q2w to set
         */
        public void setQ2w(double q2w) {
            this.q2w = q2w;
        }

        /**
         * @return the q3w
         */
        public double getQ3w() {
            return q3w;
        }

        /**
         * @param q3w the q3w to set
         */
        public void setQ3w(double q3w) {
            this.q3w = q3w;
        }

        /**
         * @return the q4w
         */
        public double getQ4w() {
            return q4w;
        }

        /**
         * @param q4w the q4w to set
         */
        public void setQ4w(double q4w) {
            this.q4w = q4w;
        }

        private double lya;
        private double lyw;

        private double q1a;
        private double q2a;
        private double q3a;
        private double q4a;

        private double q1w;
        private double q2w;
        private double q3w;
        private double q4w;

        private double cd;

        public void setCd(double cd) {
            this.cd = cd;
        }

        public double getCd() {
            return cd;
        }

        public double getTotalArrias() {
            return getLya() + getQ1a() + getQ2a() + getQ3a() + getQ4a();
        }

        public double getTotalWarrant() {
            return getLyw() + getQ1w() + getQ2w() + getQ3w() + getQ4w();
        }

        public double thisYarArrias() {
            return getQ1a() + getQ2a() + getQ3a() + getQ4a();
        }

        public double thisYarWarrant() {
            return getQ1w() + getQ2w() + getQ3w() + getQ4w();
        }

        public boolean HaveArrias() {
            if (getTotalArrias() + getTotalWarrant() + cd > 0) {
                return true;
            } else {
                return false;
            }
        }

    }

}
