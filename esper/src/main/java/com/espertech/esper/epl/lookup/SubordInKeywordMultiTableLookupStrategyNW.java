/*
 ***************************************************************************************
 *  Copyright (C) 2006 EsperTech, Inc. All rights reserved.                            *
 *  http://www.espertech.com/esper                                                     *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 ***************************************************************************************
 */
package com.espertech.esper.epl.lookup;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.core.ExprEvaluator;
import com.espertech.esper.epl.expression.core.ExprEvaluatorContext;
import com.espertech.esper.epl.join.plan.InKeywordTableLookupUtil;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.join.table.PropertyIndexedEventTableSingle;

import java.util.Collection;

/**
 * Index lookup strategy for subqueries for in-keyword single-index sided.
 */
public class SubordInKeywordMultiTableLookupStrategyNW implements SubordTableLookupStrategy {
    /**
     * Index to look up in.
     */
    protected final PropertyIndexedEventTableSingle[] indexes;

    protected final ExprEvaluator evaluator;

    protected final LookupStrategyDesc strategyDesc;

    public SubordInKeywordMultiTableLookupStrategyNW(ExprEvaluator evaluator, EventTable[] tables, LookupStrategyDesc strategyDesc) {
        this.evaluator = evaluator;
        indexes = new PropertyIndexedEventTableSingle[tables.length];
        for (int i = 0; i < tables.length; i++) {
            indexes[i] = (PropertyIndexedEventTableSingle) tables[i];
        }
        this.strategyDesc = strategyDesc;
    }

    public Collection<EventBean> lookup(EventBean[] eventsPerStream, ExprEvaluatorContext context) {
        return InKeywordTableLookupUtil.multiIndexLookup(evaluator, eventsPerStream, context, indexes);
    }

    public LookupStrategyDesc getStrategyDesc() {
        return strategyDesc;
    }

    public String toQueryPlan() {
        return this.getClass().getSimpleName();
    }
}
