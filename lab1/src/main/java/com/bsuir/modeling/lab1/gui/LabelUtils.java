package com.bsuir.modeling.lab1.gui;

import com.bsuir.modeling.lab1.constants.GUIConstants;
import com.bsuir.modeling.lab1.math.MathTools;
import com.bsuir.modeling.lab1.generator.RandomGenerator;

/**
 * Created by Vlad Kanash on 15.9.16.
 */
class LabelUtils {

    private LabelUtils() {}

    static String getExpectedValueLabel(double[] values) {
        return String.format("%s %.3f", GUIConstants.EXPECTED_VALUE_LABEL,
                MathTools.mean(values));
    }

    static String getVarianceString(double[] values) {
        return String.format("%s %.3f", GUIConstants.VARIANCE_LABEL,
                MathTools.variance(values));
    }

    static String getSkoString(double[] values) {
        return String.format("%s %.3f", GUIConstants.SKO_LABEL,
                MathTools.sko(values));
    }

    static String getCheckString(double[] values) {
        return String.format("%s %.3f", GUIConstants.CHECK_LABEL,
                MathTools.check(values));
    }

    static String getPeriodString(RandomGenerator generator) {
        return String.format("%s %d", GUIConstants.PERIOD_LABEL,
                MathTools.period(generator));
    }

    static String getAPeriodString(RandomGenerator generator) {
        return String.format("%s %d", GUIConstants.APERIOD_LABEL,
                MathTools.aperiod(generator));
    }
}
