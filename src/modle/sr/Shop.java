//package modle.sr;
//
//import com.jfoenix.controls.JFXComboBox;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//import org.hibernate.criterion.Restrictions;
//import pojo.SrBuilding;
//import pojo.SrFlow;
//import pojo.SrShop;
//import pojo.SrShopType;
//
//import java.util.HashMap;
//import java.util.List;
//
///**
// * Created by Sampath on 2019-02-09.
// */
//public class Shop {
//    public void LoadBuildings(JFXComboBox<_SrBuilding> com_building) {
//
//        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            @SuppressWarnings("unchecked") List<SrBuilding> srBuildings = session.createCriteria(SrBuilding.class).add(Restrictions.eq("srBuildingStatus", 1)).list();
//            ObservableList<_SrBuilding> buildings = FXCollections.observableArrayList();
//            for (SrBuilding building : srBuildings) {
//                buildings.add(new _SrBuilding(building));
//            }
//            com_building.setItems(buildings);
//
//        } catch (Exception e) {
//            transaction.rollback();
//            modle.Allert.notificationError("ERROR", e.getMessage());
//
//        } finally {
//            session.close();
//        }
//    }
//
//    public void LoadBuildingFlows(JFXComboBox<_SrFlow> comboBox, _SrBuilding selectedBuilding) {
//        if (selectedBuilding == null) {
//            comboBox.getItems().clear();
//            return;
//        }
//
//        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            @SuppressWarnings("unchecked") List<SrFlow> srFlows = session.createCriteria(SrFlow.class)
//                    .add(Restrictions.eq("srFlowStatus", 1))
//                    .add(Restrictions.eq("srBuilding", selectedBuilding.getSrBuilding()))
//                    .list();
//            ObservableList<_SrFlow> st = FXCollections.observableArrayList();
//            for (SrFlow srFlow : srFlows) {
//                st.add(new _SrFlow(srFlow));
//            }
//            comboBox.setItems(st);
//        } catch (Exception e) {
//            transaction.rollback();
//            modle.Allert.notificationError("ERROR", e.getMessage());
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//    }
//
//    public void LoadShopTypes(JFXComboBox<_SrShopType> comboBox) {
//        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            @SuppressWarnings("unchecked") List<SrShopType> srShopTypes = session.createCriteria(SrShopType.class).add(Restrictions.eq("srShopTypeStatus", 1)).list();
//            ObservableList<_SrShopType> st = FXCollections.observableArrayList();
//            for (SrShopType srShopType : srShopTypes) {
//                st.add(new _SrShopType(srShopType));
//            }
//            comboBox.setItems(st);
//
//        } catch (Exception e) {
//            transaction.rollback();
//            modle.Allert.notificationError("ERROR", e.getMessage());
//
//        } finally {
//            session.close();
//        }
//    }
//
//    public boolean saveShop(_SrShop _srShop, SrBuilding Building, SrFlow srFlow, SrShopType shopType, Integer no, String code, String des) {
//        boolean update = false;
//        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            SrShop srShop;
//            if (_srShop == null) {
//                srShop = new SrShop();
//            } else {
//                srShop = _srShop.getSrShop();
//                update = true;
//            }
//            srShop.setSrBuilding(Building);
//            srShop.setSrFlow(srFlow);
//            srShop.setSrShopType(shopType);
//            srShop.setSrShopNumber(no);
//            srShop.setSrShopNo(code);
//            srShop.setSrShopDis(des);
//            srShop.setSrShopStatus(1);
//            if (update) {
//                session.update(srShop);
//            } else {
//                session.save(srShop);
//            }
//            transaction.commit();
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            transaction.rollback();
//            modle.Allert.notificationError("ERROR", e.getMessage());
//            return false;
//        } finally {
//            session.close();
//        }
//
//    }
//
//    public void LoadShops(TableView<?> table_shop, TableColumn<?, ?> tc_building, TableColumn<?, ?> tc_flow, TableColumn<?, ?> tc_shopType, TableColumn<?, ?> tc_shopCode, TableColumn<?, ?> tc_shopDescription, TableColumn<?, ?> tc_shopNumber) {
//
//        Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            @SuppressWarnings("unchecked") List<SrShop> srShops = session.createCriteria(SrShop.class).add(Restrictions.eq("srShopStatus", 1)).list();
//            ObservableList<_SrShop> shops = FXCollections.observableArrayList();
//
//            for (SrShop srShop :  srShops) {
//                shops.add(new _SrShop(srShop));
//            }
//
//
//            HashMap<TableColumn, String> hashMap = new HashMap<>();
//            hashMap.put(tc_building, "srBuildingName");
//            hashMap.put(tc_flow, "srFlowName");
//            hashMap.put(tc_shopType, "srShopTypeName");
//            hashMap.put(tc_shopNumber, "srShopNumber");
//            hashMap.put(tc_shopCode, "srShopNo");
//            hashMap.put(tc_shopDescription, "srShopDis");
//            new modle.TableLoad().load(shops, table_shop, hashMap);
//
//        } catch (Exception e) {
//            transaction.rollback();
//            modle.Allert.notificationError("ERROR", e.getMessage());
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//
//
//    }
//
//
//    public static class _SrBuilding {
//
//        private final Integer id;
//        private final String name;
//        private final SrBuilding srBuilding;
//
//        public _SrBuilding(SrBuilding building) {
//            id = building.getIdsrBuilding();
//            name = building.getSrBuildingName();
//            srBuilding = building;
//        }
//
//        public SrBuilding getSrBuilding() {
//            return srBuilding;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        @Override
//        public String toString() {
//            return getName();
//        }
//    }
//
//    public static class _SrFlow {
//        private final SrFlow srFlow;
//
//        public _SrFlow(SrFlow building) {
//            srFlow = building;
//        }
//
//        public SrFlow getSrFlow() {
//            return srFlow;
//        }
//
//        @Override
//        public String toString() {
//            return srFlow.getSrFlowName();
//        }
//    }
//
//    public static class _SrShopType {
//        private final SrShopType srShopType;
//
//        public _SrShopType(SrShopType srShopType) {
//            this.srShopType = srShopType;
//        }
//
//        public SrShopType getSrShopType() {
//            return srShopType;
//        }
//
//        @Override
//        public String toString() {
//            try {
//                return srShopType.getSrShopTypeName();
//            } catch (Exception e) {
//                return "";
//            }
//        }
//
//    }
//
//    public class _SrShop {
//        private final SrShop srShop;
//        private final String srShopNo;
//        private final Integer srShopNumber;
//        private final Integer srShopStatus;
//        private final String srShopDis;
//        private final String cusname;
//        private final String address;
//        private final String srBuildingName;
//        private final String srFlowName;
//        private final String srShopTypeName;
//
//        public _SrShop(SrShop srShop) {
//            this.srShop = srShop;
//            this.srBuildingName = srShop.getSrBuilding() == null ? "" : srShop.getSrBuilding().getSrBuildingName();
//            this.srFlowName = srShop.getSrFlow() == null ? "" : srShop.getSrFlow().getSrFlowName();
//            this.srShopTypeName = srShop.getSrShopType() == null ? "" : srShop.getSrShopType().getSrShopTypeName();
//            this.srShopNo = srShop.getSrShopNo();
//            this.srShopNumber = srShop.getSrShopNumber();
//            this.srShopStatus = srShop.getSrShopStatus();
//            this.srShopDis = srShop.getSrShopDis();
//            this.cusname = srShop.getCusname();
//            this.address = srShop.getAddress();
////           this._srBuilding=new _SrBuilding(srShop.getSrBuilding());
//
//        }
//
//        public SrShop getSrShop() {
//            return srShop;
//        }
//
//        public String getSrShopNo() {
//            return srShopNo;
//        }
//
//        public Integer getSrShopNumber() {
//            return srShopNumber;
//        }
//
//        public String getSrShopDis() {
//            return srShopDis;
//        }
//
//        public Integer getSrShopStatus() {
//            return srShopStatus;
//        }
//
//        public String getCusname() {
//            return cusname;
//        }
//
//        public String getAddress() {
//            return address;
//        }
//
//        public String getSrBuildingName() {
//            return srBuildingName;
//        }
//
//        public String getSrFlowName() {
//            return srFlowName;
//        }
//
//        public String getSrShopTypeName() {
//            return srShopTypeName;
//        }
//    }
//}
