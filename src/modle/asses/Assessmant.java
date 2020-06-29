/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modle.asses;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import conn.DB;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import pojo.Assessment;
import pojo.Street;

/**
 * @author RM.LasanthaRanga@gmail.com
 */
public class Assessmant {

    public ArrayList<String> getAssessmantNoListByStreetAndWard(String ward, String Street) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {

            ResultSet data = DB.getData("SELECT\n" +
                    "\tward.ward_name,\n" +
                    "\tstreet.street_name,\n" +
                    "\tassessment.assessment_no,\n" +
                    "\tassessment.idAssessment\n" +
                    "FROM\n" +
                    "\tward\n" +
                    "INNER JOIN street ON street.Ward_idWard = ward.idWard\n" +
                    "INNER JOIN assessment ON assessment.Street_idStreet = street.idStreet\n" +
                    "AND assessment.Ward_idWard = ward.idWard\n" +
                    "WHERE\n" +
                    "\tward.ward_name = '"+ward+"'\n" +
                    "AND street.street_name = '"+Street+"'");
            ArrayList<String> list = new ArrayList<>();
            while (data.next()) {
                    list.add(data.getString("assessment_no"));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Assessment> getAssessmantListByStreetAndWard(String ward, String Street) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {

            ResultSet data = DB.getData("SELECT\n" +
                    "\tward.ward_name,\n" +
                    "\tstreet.street_name,\n" +
                    "\tassessment.assessment_no,\n" +
                    "\tassessment.idAssessment\n" +
                    "FROM\n" +
                    "\tward\n" +
                    "INNER JOIN street ON street.Ward_idWard = ward.idWard\n" +
                    "INNER JOIN assessment ON assessment.Street_idStreet = street.idStreet\n" +
                    "AND assessment.Ward_idWard = ward.idWard\n" +
                    "WHERE\n" +
                    "\tward.ward_name = '"+ward+"'\n" +
                    "AND street.street_name = '"+Street+"'");
            List<Assessment> list = new ArrayList<>();
            while (data.next()) {
                Assessment idAssessment = (Assessment) session.load(Assessment.class, data.getInt("idAssessment"));
                list.add(idAssessment);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }





    public int isExistAssessmant(String w, String s, String a) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            Street street = (Street) session.createCriteria(Street.class).add(Restrictions.eq("ward", (pojo.Ward) session.createCriteria(pojo.Ward.class).add(Restrictions.eq("wardName", w)).list().get(0))).list().get(0);
            Criteria criteria = session.createCriteria(Assessment.class);
            criteria.add(Restrictions.eq("street", street));
            criteria.add(Restrictions.eq("assessmentNo", a));
            criteria.add(Restrictions.eq("assessmentStatus", 1));
            List<Assessment> list = criteria.list();
            Assessment get = list.get(0);
            return get.getIdAssessment();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            session.close();
        }
    }

    public boolean isExsist(String ward, String street, String ass) {
        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        try {
            Criteria criteria = session.createCriteria(Assessment.class);
            pojo.Ward war = (pojo.Ward) session.createCriteria(pojo.Ward.class).add(Restrictions.eq("wardName", ward)).uniqueResult();
            Criteria cry = session.createCriteria(Street.class);
            cry.add(Restrictions.eq("ward", war));
            Street st = (Street) cry.add(Restrictions.eq("streetName", street)).uniqueResult();
            criteria.add(Restrictions.eq("street", st));
            List list = criteria.add(Restrictions.eq("assessmentNo", ass)).list();
            if (list.size() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        } finally {
            session.close();
        }

    }


    public String getAssOwnerNameByWSA(String ward, String street, String assess) {
        System.out.println("call");
        String cusName = "";
        try {
            String qq = "SELECT\n" +
                    "\tward.ward_name,\n" +
                    "\tstreet.street_name,\n" +
                    "\tassessment.assessment_no,\n" +
                    "\tcustomer.cus_name\n" +
                    "FROM\n" +
                    "\tward\n" +
                    "INNER JOIN street ON street.Ward_idWard = ward.idWard\n" +
                    "INNER JOIN assessment ON assessment.Street_idStreet = street.idStreet\n" +
                    "AND assessment.Ward_idWard = ward.idWard\n" +
                    "INNER JOIN customer ON assessment.Customer_idCustomer = customer.idCustomer\n" +
                    "WHERE\n" +
                    "\tward.ward_name = '" + ward + "'\n" +
                    "AND street.street_name = '" + street + "'\n" +
                    "AND assessment.assessment_no = '" + assess + "'";

            ResultSet data = DB.getData(qq);

            if (data.first()) {
                cusName = data.getString("cus_name");
                System.out.println(cusName);
            } else {
                System.out.println("else");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return cusName;
    }


}
