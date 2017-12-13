package viewer;

import lab2.State;
import lab2.Transition;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.Node;

import java.io.InputStream;
import java.util.Scanner;
import java.util.Set;

public class StateMachineGraphViewer {

    private final SingleGraph graph = new SingleGraph("FSM-1");

    public StateMachineGraphViewer(final Set<Transition> transitions,
                                   final Set<State> states,
                                   final State startState,
                                   final Set<State> endStates) {
        graph.setStrict(false);
        graph.setAutoCreate(true);
        graph.addAttribute("ui.stylesheet", getStylesheet());

        initNodes(states, startState, endStates);
        initEdges(transitions);

        graph.display();
    }

    private void initNodes(Set<State> states, State startState, Set<State> endStates) {
        states.forEach(state -> {
                    Node node = graph.addNode(state.toString());
                    node.addAttribute("state", state);
                });

        graph.getEachNode().forEach(node -> {
            node.addAttribute("ui.label", node.getId());

            if (node.getAttribute("state", State.class).equals(startState)) {
                node.addAttribute("ui.class", "start");
            } else if (endStates.contains(node.getAttribute("state", State.class))) {
                node.addAttribute("ui.class", "end");
            }
        });
    }

    private void initEdges(Set<Transition> transitions) {
        transitions.forEach(transition -> graph.addEdge(
                transition.inputState().toString() + transition.inputSymbol() + transition.outputState(),
                transition.inputState().toString(),
                transition.outputState().toString(), true));

        graph.getEachEdge().forEach(edge -> edge.addAttribute("ui.label", edge.getId().charAt(1)));

        graph.getEachEdge().forEach(edge -> {
            if (edge.getId().charAt(0) == edge.getId().charAt(2)) {
                graph.getNode(String.valueOf(edge.getId().charAt(0)))
                        .addAttribute("ui.class", "looped");
            }
        });
    }

    private String getStylesheet() {
        InputStream in = getClass().getResourceAsStream("/stylesheet.css");
        Scanner scanner = new Scanner(in).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }
}
