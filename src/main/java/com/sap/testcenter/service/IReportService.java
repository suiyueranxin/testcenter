package com.sap.testcenter.service;

import java.util.List;

public interface IReportService {
	public void initialData(String name);
	public String queryReport(String name);
	public String list(List<String> fields, List<String> filters);
	public String countBy(List<String> by, List<String> filters);
	public String calcReportStatus(List<String> calculators);
	public String calcReportWeek(String startDate, String endDate);
}
