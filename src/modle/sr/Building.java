//package modle.sr;
//
//import javafx.beans.property.SimpleStringProperty;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//import org.hibernate.criterion.Restrictions;
//import pojo.SrBuilding;
//import pojo.SrFlow;
//
//import java.util.HashMap;
//import java.util.List;
//
///**
// * @author Sampath
// */
//public class Building {
//
//    public boolean save(_srBuilding _srBuilding, String bname, String bcode) {
//        boolean update = true;
//        SrBuilding srBuilding;
//        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
//        Transaction beginTransaction = session.beginTransaction();
//        try {
//            if (_srBuilding == null) {
//                srBuilding = new SrBuilding();
//                update = false;
//            } else {
//                srBuilding = _srBuilding.getBuilding();
//            }
//            srBuilding.setSrBuildingName(bname);
//            srBuilding.setSrBuildingCode(bcode);
//            srBuilding.setSrBuildingStatus(1);
//            if (update) {
//                session.update(srBuilding);
//            } else {
//                session.save(srBuilding);
//            }
//            beginTransaction.commit();
//            modle.Allert.notificationGood(update ? "Building Updated" : "Building Saved", bname);
//            return true;
//        } catch (Exception e) {
//            beginTransaction.rollback();
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
////88lasantha@gmail.com
//        return false;
//    }
//
//    public void buildingTableLoad(TableView<?> tbl_Building, TableColumn<?, ?> col_b_name, TableColumn<?, ?> col_b_code) {
//        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            @SuppressWarnings("unchecked") List<SrBuilding> srBuildings = session.createCriteria(SrBuilding.class).add(Restrictions.eq("srBuildingStatus", 1)).list();
//            ObservableList<_srBuilding> buildings = FXCollections.observableArrayList();
//            for (SrBuilding building : srBuildings) {
//                buildings.add(new _srBuilding(building, building.getSrBuildingName(), building.getSrBuildingCode()));
//            }
//
//            HashMap<TableColumn, String> hashMap = new HashMap<>();
//            hashMap.put(col_b_name, "Name");
//            hashMap.put(col_b_code, "Code");
//            new modle.TableLoad().load(buildings, tbl_Building, hashMap);
//
//        } catch (Exception e) {
//            transaction.rollback();
//            modle.Allert.notificationError("ERROR", e.getMessage());
//
//        } finally {
//            session.close();
//        }
//
//    }
//
//    @SuppressWarnings("unchecked")
//    public void flowTableLoad(_srBuilding selectedItem, TableView<?> tbl_Building_flow, TableColumn<?, ?> col_f_name, TableColumn<?, ?> col_f_code, TableColumn<?, ?> col_f_no) {
//
//        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            @SuppressWarnings("unchecked") List<SrFlow> srFlows = session.createCriteria(SrFlow.class)
//                    .add(Restrictions.eq("srFlowStatus", 1))
//                    .add(Restrictions.eq("srBuilding", selectedItem.getBuilding()))
//                    .list();
//            ObservableList<_srBuildingFlows> buildingFlows = FXCollections.observableArrayList();
//            for (SrFlow srFlow : srFlows) {
//                buildingFlows.add(new _srBuildingFlows(srFlow));
//            }
//
//            HashMap<TableColumn, String> hashMap = new HashMap<>();
//            hashMap.put(col_f_name, "srFlowName");
//            hashMap.put(col_f_code, "srFlowCode");
//            hashMap.put(col_f_no, "srFlowNumber");
//            new modle.TableLoad().load(buildingFlows, tbl_Building_flow, hashMap);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            transaction.rollback();
//            modle.Allert.notificationError("ERROR", e.getMessage());
//
//        } finally {
//            session.close();
//        }
//
//    }
//
//    public boolean saveFlow(_srBuildingFlows _srFlow, SrBuilding buiding, String fname, String fcode, int fno) {
//        boolean update = true;
//        SrFlow srFlow;
//        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
//        Transaction beginTransaction = session.beginTransaction();
//        try {
//            if (_srFlow == null) {
//                srFlow = new SrFlow();
//                update = false;
//            } else {
//                srFlow = _srFlow.getSrFlow();
//            }
//            srFlow.setSrFlowName(fname);
//            srFlow.setSrBuilding(buiding);
////            srFlow.setSrFlowCode(fcode);
//            srFlow.setSrFlowNumber(fno);
//            srFlow.setSrFlowStatus(1);
//
//            if (update) {
//                session.update(srFlow);
//            } else {
//                session.save(srFlow);
//            }
//
//            beginTransaction.commit();
//            modle.Allert.notificationGood(update ? "Building Flow Updated" : "Building Flow Saved", fname);
//            return true;
//        } catch (Exception e) {
//            beginTransaction.rollback();
//            return false;
//        } finally {
//            session.close();
//        }
//    }
//
//    public class _srBuildingFlows {
//        private final SimpleStringProperty srFlowName;
////        private final SimpleStringProperty srFlowCode;
//        private final Integer srFlowNumber;
//        private final SrFlow srFlow;
//
//        public _srBuildingFlows(SrFlow srFlow) {
//            this.srFlow = srFlow;
//            this.srFlowName = new SimpleStringProperty(srFlow.getSrFlowName());
////            this.srFlowCode = new SimpleStringProperty(srFlow.getSrFlowCode());
//            this.srFlowNumber = srFlow.getSrFlowNumber();
//        }
//
//        public String getSrFlowName() {
//            return srFlowName.get();
//        }
//
////        public String getSrFlowCode() {
////            return srFlowCode.get();
////        }
//
//        public Integer getSrFlowNumber() {
//            return srFlowNumber;
//        }
//
//        public SrFlow getSrFlow() {
//            return srFlow;
//        }
//    }
//
//
//    public class _srBuilding {
//
//
//        private final SimpleStringProperty Name;
//        private final SimpleStringProperty Code;
//        private final SrBuilding building;
//
//        public _srBuilding(SrBuilding building, String name, String code) {
//            this.building = building;
//            Name = new SimpleStringProperty(name);
//            Code = new SimpleStringProperty(code);
//        }
//
//
//        public String getName() {
//            return Name.get();
//        }
//
//        public String getCode() {
//            return Code.get();
//        }
//
//        public SrBuilding getBuilding() {
//            return building;
//        }
//    }
//
//}
