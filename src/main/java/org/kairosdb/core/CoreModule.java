/*
 * Copyright 2013 Proofpoint Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.kairosdb.core;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import org.kairosdb.core.aggregator.*;
import org.kairosdb.core.groupby.*;
import org.kairosdb.core.http.rest.json.GsonParser;
import org.kairosdb.core.jobs.CacheFileCleaner;
import org.kairosdb.core.reporting.KairosMetricRegistry;
import org.kairosdb.core.scheduler.KairosDBScheduler;
import org.kairosdb.util.Util;

import java.util.Properties;

public class CoreModule extends AbstractModule
{
	private Properties m_props;

	public CoreModule(Properties props)
	{
		m_props = props;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void configure()
	{
		bind(AggregatorFactory.class).to(GuiceAggregatorFactory.class).in(Singleton.class);
		bind(GroupByFactory.class).to(GuiceGroupByFactory.class).in(Singleton.class);
		bind(GsonParser.class).in(Singleton.class);
		bind(KairosMetricRegistry.class).in(Singleton.class);
		bind(CacheFileCleaner.class).in(Singleton.class);
		bind(KairosDBScheduler.class).in(Singleton.class);

		bind(SumAggregator.class).in(Singleton.class);
		bind(MinAggregator.class).in(Singleton.class);
		bind(MaxAggregator.class).in(Singleton.class);
		bind(AvgAggregator.class).in(Singleton.class);
		bind(StdAggregator.class).in(Singleton.class);
		bind(RateAggregator.class).in(Singleton.class);

		bind(ValueGroupBy.class).in(Singleton.class);
		bind(TimeGroupBy.class).in(Singleton.class);
		bind(TagGroupBy.class).in(Singleton.class);

		Names.bindProperties(binder(), m_props);

		String hostname = m_props.getProperty("kairosdb.hostname");
		bindConstant().annotatedWith(Names.named("HOSTNAME")).to(hostname != null ? hostname: Util.getHostName());
	}
}
