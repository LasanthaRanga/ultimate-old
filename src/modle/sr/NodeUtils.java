package modle.sr;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sampath on 2019-02-09.
 */
public class NodeUtils {

    private static <T extends Pane> List<Node> paneNodes(T parent) {
        return paneNodes(parent, new ArrayList<>());
    }

    private static <T extends Pane> List<Node> paneNodes(T parent, List<Node> nodes) {
        for (Node node : parent.getChildren()) {
            if (node instanceof Pane) {
                paneNodes((Pane) node, nodes);
            } else {
                nodes.add(node);
            }
        }

        return nodes;
    }

    @SuppressWarnings("unchecked")
    public static void cleanNodsIn(Pane form) {
        final List<Node> nodes = paneNodes(form);
        for (Node n : nodes) {
            if (n instanceof ComboBox) {
                ComboBox c = (ComboBox) n;
                c.getSelectionModel().select(-1);
                //noinspection unchecked
                c.setValue(null);
            } else if (n instanceof TextField) {
                TextField t = (TextField) n;
                t.setText("");
            } else if (n instanceof TextArea) {
                TextArea t = (TextArea) n;
                t.setText("");
            }
        }
    }
}