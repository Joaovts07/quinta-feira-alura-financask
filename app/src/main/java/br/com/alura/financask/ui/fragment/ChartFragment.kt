package br.com.alura.financask.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.alura.financask.R
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry


class ChartFragment: Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?)
            : View? {
        return inflater.inflate(
                R.layout.pie_chart,
                container,
                false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pie = AnyChart.pie()
        val data: MutableList<DataEntry> = ArrayList()
        data.add(ValueDataEntry("despesa1", 10000))
        data.add(ValueDataEntry("despesa2", 12000))
        data.add(ValueDataEntry("despesa3", 18000))
        pie.data(data)
        val anyChartView = view.findViewById<AnyChartView>(R.id.any_chart_view)
        anyChartView.setChart(pie)
    }


}