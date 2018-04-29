package adamsiot

import grails.plugin.springsecurity.annotation.Secured

class CatfeederController {

    @Secured(['ROLE_ADMIN'])
    def index() {
        render(view: "CatFeeder")
    }
}
