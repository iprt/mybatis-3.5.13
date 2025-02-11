package org.iproute.logfunc;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * SrcLog
 * <p>
 * 构造一个阅读源码的日志树
 * <p>
 * 源码阅读的关键是理解源码的执行流程，这个类的目的是帮助理解源码的执行流程
 *
 * @author tech@intellij.io
 * @since 2025-01-16
 */
@Slf4j
public class SrcLog {
    // ANSI escape codes for colors
    static final String RESET = "\033[0m";  // Text Reset
    static final String RED = "\033[0;31m";     // RED
    static final String GREEN = "\033[0;32m";   // GREEN
    static final String YELLOW = "\033[0;33m";  // YELLOW
    static final String BLUE = "\033[0;34m";    // BLUE
    static final String PURPLE = "\033[0;35m";  // PURPLE
    static final String CYAN = "\033[0;36m";    // CYAN
    static final String WHITE = "\033[0;37m"; // WHITE
    static final SrcLog instance = new SrcLog();

    public static SrcLog get() {
        return instance;
    }

    private final List<Group> groups;

    private final AtomicReference<Node> lastNode = new AtomicReference<>(null);

    private SrcLog() {
        groups = new ArrayList<>();
    }

    public void addGroup(String groupName) {
        if (groups.isEmpty()) {
            groups.add(new Group(groupName, 0));
        } else {
            Group lastGroup = groups.get(groups.size() - 1);
            groups.add(new Group(groupName, lastGroup.index + 1));
        }
    }

    public SrcLog addNode(Object object, String function, String... functionParams) {
        if (groups.isEmpty()) {
            throw new RuntimeException("请先添加 group");
        }
        Group lastGroup = groups.get(groups.size() - 1);
        lastGroup.addNode(object, function, functionParams == null ? new ArrayList<>(0) : Arrays.asList(functionParams));
        return this;
    }

    public void logMsg(String fmt, Object... args) {
        if (Objects.isNull(lastNode.get())) {
            throw new RuntimeException("请先添加 node");
        }
        Node node = lastNode.get();
        node.setType(NodeType.COMMONS);
        node.setLogMsg(String.format(fmt, args));
    }

    public void tips(String fmt, Object... args) {
        if (groups.isEmpty()) {
            throw new RuntimeException("请先添加 group");
        }
        Node node = lastNode.get();
        node.setType(NodeType.TIPS);
        node.setLogMsg(String.format(fmt, args));
    }

    enum NodeType {
        COMMONS,
        TIPS,
    }

    @Data
    class Node {
        private Group group;
        private NodeType type;
        private String className;
        private String function;
        private List<String> functionParams;

        /**
         * level的自增原则（同一个group下）
         * <p>
         * 判断当前的 class 和 function 是否和上一个相同
         * <p>
         * 如果相同，level不变
         * <p>
         * 如果不同，level + 1
         */
        private int level;

        /**
         * 对于执行方法的描述，便于源码理解
         */
        private String logMsg;

        public Node(Group group, String className, String function, List<String> functionParams, int level, String logMsg) {
            this.group = group;
            this.className = className;
            this.function = function;
            this.functionParams = functionParams;
            this.level = level;
            this.logMsg = logMsg;
        }
    }

    @Data
    class Group {
        /**
         * group是怎么定义的？
         * <p>
         * 方法执行的片段，比如 SqlSessionFactoryBuilder.build(InputStream,String,Properties)
         * <p>
         * 为什么要定义 group？
         */
        private final String name;

        /**
         * 自增的index
         * <p>
         * 每一个方法块都会开辟一个新的 index
         */
        private final int index;

        /**
         * 一个group下的所有节点
         */
        private final List<Node> nodes;


        private final Map<String, Integer> nodeLevelMap;

        public Group(String name, int index) {
            this.name = name;
            this.index = index;
            this.nodes = new ArrayList<>();
            this.nodeLevelMap = new HashMap<>();
        }

        @Override
        public boolean equals(Object object) {
            if (object == null || getClass() != object.getClass()) return false;
            Group that = (Group) object;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(name);
        }

        void addNode(Object object, String function, List<String> functionParams) {
            String key = getKey(object, function);

            if (nodes.isEmpty()) {
                Node node = new Node(this, getClassName(object), function, functionParams, 0, "");
                nodes.add(node);
                nodeLevelMap.put(key, node.getLevel());
                lastNode.set(node);
                return;
            }

            Node last = nodes.get(nodes.size() - 1);
            String lastKey = last.getClassName() + "." + last.getFunction();
            Integer i = nodeLevelMap.get(key);

            int newLevel;

            if (Objects.nonNull(i)) {
                newLevel = i;
            } else {
                if (lastKey.equals(key)) {
                    newLevel = last.getLevel();
                } else {
                    newLevel = last.getLevel() + 1;
                }
            }

            Node node = new Node(this, getClassName(object), function, functionParams, newLevel, "");

            nodes.add(node);
            nodeLevelMap.put(key, node.getLevel());
            lastNode.set(node);

        }

        String getClassName(Object object) {
            if (object instanceof String) {
                return object.toString();
            }
            return object.getClass().getName();
        }

        String getKey(Object object, String function) {
            if (object instanceof String) {
                return object + "." + function;
            }

            if (object instanceof Class) {
                return ((Class<?>) object).getName() + "." + function;
            }
            return object.getClass().getName() + "." + function;
        }
    }


    public void printLog() {
        if (groups.isEmpty()) {
            return;
        }

        String levelSpace = "\t";

        groups.forEach(group -> {
            System.out.println(YELLOW + "group=" + group.getName() + RESET);
            group.getNodes().forEach(node -> {
                if (node.getType() == NodeType.TIPS) {
                    int level = node.getLevel();
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < level; i++) {
                        sb.append(levelSpace);
                    }
                    sb.append(RED).append("tips : ").append(node.getLogMsg()).append(RESET);
                    System.out.println(sb);
                    return;
                }

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < node.getLevel(); i++) {
                    sb.append("\t");
                }
                sb.append(BLUE).append(node.getClassName()).append(RESET);
                sb.append(" ");

                sb.append(GREEN).append(node.getFunction()).append(RESET);

                sb.append(PURPLE).append("(");
                if (!node.getFunctionParams().isEmpty()) {
                    for (int i = 0; i < node.getFunctionParams().size(); i++) {
                        sb.append(node.getFunctionParams().get(i));
                        if (i != node.getFunctionParams().size() - 1) {
                            sb.append(", ");
                        }
                    }
                }
                sb.append(")").append(RESET);

                if (!node.getLogMsg().isEmpty()) {
                    sb.append(" : ").append(node.getLogMsg());
                }

                System.out.println(sb);
            });

        });

    }

}
