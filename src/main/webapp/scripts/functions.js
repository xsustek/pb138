var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = window.location.search.substring(1),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
        }
    }
};

const setUrlParams = (name, value) => {
    let params = new URLSearchParams(location.search)
    params.set(name, value)
    window.history.pushState("", "", `${location.origin}?${params.toString()}`)
}

const getNodeValue = (doc, node) => doc.getElementsByTagName(node)[0].childNodes[0].nodeValue

const parseXml = xml => ({
    count: getNodeValue(xml, "count"),
    damage: getNodeValue(xml, "damage"),
    street: getNodeValue(xml, "street"),
    withWeaponCount: getNodeValue(xml, "withWeaponCount"),
    region: getNodeValue(xml, "region"),
    zlocin: getNodeValue(xml, "zlocin"),
    precin: getNodeValue(xml, "precin"),
    planned: getNodeValue(xml, "planned"),
    prepared: getNodeValue(xml, "prepared"),
    executed: getNodeValue(xml, "executed"),
    cold: getNodeValue(xml, "cold"),
    average_time: getNodeValue(xml, "averageTime"),
    average_cases: getNodeValue(xml, "averageCases"),
    preparators: getNodeValue(xml, "preparators"),
    population: getNodeValue(xml, "population")
});

const fillValues = values => {
    $("#region").text(values.region);
    $("#crime_count").text(values.count);
    $("#crime_damage").text(formatNumber(values.damage) + " Kč");
    $("#crime_on_street").text(values.street);
    $("#crime_weapon").text(values.withWeaponCount);
    $("#crime_zlocin").text(values.zlocin);
    $("#crime_precin").text(values.precin);
    $("#crime_prepared").text(values.prepared);
    $("#crime_planned").text(values.planned);
    $("#crime_executed").text(values.executed);
    $("#crime_cold").text(values.cold);
    $("#crime_average_time").text(values.average_time + " dní");
    $("#crime_average_cases").text(values.average_cases);
    $("#crime_preparators").text(values.preparators);
    $("#population").text(values.population);
};

const setMap = region => {
    if (region === undefined) {
        $("#map").attr("src", "maps/start.png");
    } else {
        $("#map").attr("src", "maps/" + region + ".png");
    }
};

const setSelect = region => $("#inlineFormCustomSelect").val(region);

const formatNumber = value => {
    var num = value;
    var result = "";
    var gap_size = 3;

    while (num.length > 0)
    {
        result = num.substring(num.length - gap_size, num.length) + " " + result;
        num = num.substring(0, num.length - gap_size);
    }

    return result;
}

$(document).ready(function () {
    let region = getUrlParameter('region');
    let number = $("#crime_damage").val();
    let number2 = $("#crime_average_time").val();
    setMap(region);
    setSelect(region);
    //$("#crime_damage").text(formatNumber(number) + " Kč");
    //$("#crime_average_time").text(number2 + " dní");
    $("#inlineFormCustomSelect").change(e => {
        let region = $("#inlineFormCustomSelect").val();
        fetch(`/api/crimes/${region}`)
            .then(r => r.text())
            .then(r => (new window.DOMParser()).parseFromString(r, "text/xml"))
            .then(r => parseXml(r))
            .then(r => {
                fillValues(r);
                setMap(region);
                setUrlParams("region", region);
            });
    });
});