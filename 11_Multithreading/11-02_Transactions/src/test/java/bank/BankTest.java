package bank;

import junit.framework.TestCase;
import junit.framework.TestResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Random;

@ExtendWith(Extension.class)
public class BankTest extends TestCase {

    @Mock
    private HashMap<String, Account> accounts;
    @Mock
    private Random random;
    @InjectMocks
    private Bank underTest;

    public BankTest() {
        super();
    }

    public BankTest(String name) {
        super(name);
    }

    @Override
    public int countTestCases() {
        return super.countTestCases();
    }

    @Override
    protected TestResult createResult() {
        return super.createResult();
    }

    @Override
    public TestResult run() {
        return super.run();
    }

    @Override
    public void run(TestResult result) {
        super.run(result);
    }

    @Override
    public void runBare() throws Throwable {
        super.runBare();
    }

    @Override
    protected void runTest() throws Throwable {
        super.runTest();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }


    @Nested
    class WhenCheckingIfIsFraud {
        private final String FROM_ACCOUNT_NUM = "FROM_ACCOUNT_NUM";
        private final String TO_ACCOUNT_NUM = "TO_ACCOUNT_NUM";
        private final long AMOUNT = 99;

        @BeforeEach
        void setup() {
        }
    }

    @Nested
    class WhenTransfering {
        private final String FROM_ACCOUNT_NUM = "FROM_ACCOUNT_NUM";
        private final String TO_ACCOUNT_NUM = "TO_ACCOUNT_NUM";
        private final long AMOUNT = 2;

        @BeforeEach
        void setup() {
        }
    }

    @Nested
    class WhenGettingBalance {
        private final String ACCOUNT_NUM = "ACCOUNT_NUM";

        @BeforeEach
        void setup() {
        }
    }
}
