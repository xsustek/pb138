<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Project Brno - PB138</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    </head>
    <body style="padding: 50px 0;">
        <div class="container-fluid">
            <div class="row">
                <div class="col" style="float: none;margin: 0 auto;">
                    <h1 style="text-align: center">Project Brno</h1>
                </div>
            </div>
            <div class="row">
                <div class="col" style="float: none;margin: 0 auto;">
                    <h5 style="text-align: center">PB138</h5>
                </div>
            </div>

            <div class="row justify-content-center">
                <div class="col-6">
                    <label class="label" for="inlineFormCustomSelect">Zvolte Městkou Část</label>
                    <select class="form-control form-control-lg" id="inlineFormCustomSelect" name="region" style="width: 100%">
                        <option value="brno" selected>Celé Brno</option>
                        <option value="bys">Bystrc</option>
                        <option value="kom">Komárov</option>
                        <option value="krp">Královo Pole</option>
                        <option value="svr">Sever</option>
                        <option value="str">Střed</option>
                        <option value="vys">Výstavište</option>
                        <option value="zbv">Žabovřesky</option>
                        <option value="zid">Židenice</option>
                        <option value="dpv">OŽP A Doprovodu Vlaků</option>
                    </select>
                </div>
                <div class="col-6 text-center align-middle h3" id="region">${nazov}</div>
            </div>

            <div class="row justify-content-center" style="margin: 50px 0;">
                <div class="col-6">
                    <img id="map" src="/maps/start.png" />
                </div>
                <div class="col-6 text-center" id="stats">
                    <table class="table text-left">
                        <tbody>
                            <tr>
                                <td scope="row">Trestných činov celkom:</td>
                                <td id="crime_count">${celkom}</td>
                            </tr>
                            <tr>
                                <td scope="row">Celková škoda trestných činov:</td>
                                <td id="crime_damage">${skoda} Kč</td>
                            </tr>
                            <tr>
                                <td scope="row">Počet trestných činov spáchaných na ulici:</td>
                                <td id="crime_on_street">${ulica}</td>
                            </tr>
                            <tr>
                                <td scope="row">Počet trestných činov spáchaných so zbraňov:</td>
                                <td id="crime_weapon">${zbran}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <script src="/scripts/functions.js"></script>
    </body>
</html>