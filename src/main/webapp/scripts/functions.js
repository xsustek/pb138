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
    region: getNodeValue(xml, "region")
});

const fillValues = values => {
    $("#region").text(values.region);
    $("#crime_count").text(values.count);
    $("#crime_damage").text(values.damage);
    $("#crime_on_street").text(values.street);
    $("#crime_weapon").text(values.withWeaponCount);
};

const setMap = region => {
    if (region === undefined) {
        $("#map").attr("src", "maps/start.png");
    } else {
        $("#map").attr("src", "maps/" + region + ".png");
    }
};

const setSelect = region => $("#inlineFormCustomSelect").val(region);


$(document).ready(function () {
    let region = getUrlParameter('region');
    setMap(region);
    setSelect(region);
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