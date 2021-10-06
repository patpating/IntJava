package ttl.larku.junit5;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.TestSource;
import org.junit.platform.engine.TestTag;
import org.junit.platform.engine.support.descriptor.ClassSource;
import org.junit.platform.engine.support.descriptor.MethodSource;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.LoggingListener;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.util.Set;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.launcher.TagFilter.includeTags;

/**
 * A custom JUnit 5 Launcher.
 */
public class CustomDiscoveryLaunch {

    public static void main(String[] args) {
        CustomDiscoveryLaunch cdl = new CustomDiscoveryLaunch();
        cdl.discoverAndLaunch();
    }

    public void discoverAndLaunch() {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(
                        //selectPackage("ttl.larku.junit5"),
                        selectClass(JUnit5Tags.class)
                ).filters(
                        //excludeTags("integration")
                        includeTags("fast | integration")
//                        includeTags("fast")
                ).build();

        Launcher launcher = LauncherFactory.create();

        SummaryGeneratingListener listener = new SummaryGeneratingListener() {
            @Override
            public void executionStarted(TestIdentifier tid) {
                System.out.println("Test started: " + tid.getDisplayName());
                //This is necessary if you want the summary
                super.executionStarted(tid);
            }

            @Override
            public void executionFinished(TestIdentifier tid, TestExecutionResult testExecutionResult) {
                String dispName = tid.getDisplayName();
                Set<TestTag> tags = tid.getTags();
                boolean isTest = tid.isTest();
                boolean isContainer = tid.isContainer();
                TestSource ts = tid.getSource().orElse(null);
                String source = ts != null ? getSourceInfo(ts) : "NA";

                System.out.println("Test Ended: " + dispName +
                        "\n\t is Test: " + isTest +
                        "\n\t is Container: " + isContainer +
                        "\n\t Test Source: " + source +
                        "\n\t result: " + testExecutionResult.toString());
//                System.out.println("\tResult: " + testExecutionResult );

                super.executionFinished(tid, testExecutionResult);
            }
        };


        launcher.registerTestExecutionListeners(listener);

        launcher.execute(request);

        TestExecutionSummary summary = listener.getSummary();
        System.out.println("Containers run: " + summary.getContainersStartedCount() +
                ", Containers passed: " + summary.getContainersSucceededCount() +
                ", Containers aborted: " + summary.getContainersAbortedCount() +
                ", Containers failed: " + summary.getContainersFailedCount());

        System.out.println("Tests run: " + summary.getTestsStartedCount() +
                ", Tests passed: " + summary.getTestsSucceededCount() +
                ", Tests aborted: " + summary.getTestsAbortedCount() +
                ", Tests failed: " + summary.getTestsFailedCount());

        System.out.println("All Done");
    }

    public void discoverAndLaunchWithLoggingListener() {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(
                        //selectPackage("ttl.larku.junit5"),
                        selectClass(JUnit5Tags.class)
                ).filters(
                        //excludeTags("integration")
                        includeTags("fast | integration")
//                        includeTags("fast")
                ).build();

        Launcher launcher = LauncherFactory.create();

        LoggingListener ll = LoggingListener.forBiConsumer((t, sup) -> {
            if(t != null) {
                System.out.println("Exception: " + t);
            } else {
                String result = sup.get();
                System.out.println("Result: " + result);
            }
        });
        launcher.registerTestExecutionListeners(ll);


        launcher.execute(request);

        System.out.println("All Done");
    }

    public String getSourceInfo(TestSource source) {
        if(source instanceof MethodSource) {
            MethodSource ms = (MethodSource)source;
            return getSourceInfo(ms);
        }else if(source instanceof ClassSource) {
            ClassSource cs = (ClassSource)source;
            return getSourceInfo(cs);
        } else {
            return "We don't care about this source: " + source.getClass();
        }
    }

    public String getSourceInfo(MethodSource source) {
        String result = "MethodSource: " + source.getClassName()
                + "::" + source.getMethodName() + "::"
                + source.getMethodParameterTypes();
        return result;
    }

    public String getSourceInfo(ClassSource source) {
        String result = "ClassSource: " + source.getClassName()
                + ", position: " + source.getPosition().orElse(null);
        return result;
    }
}
