package adamsiot

import grails.plugin.springsecurity.annotation.Secured

class AboutController {

    @Secured(['ROLE_ADMIN'])
    def index() {
        render(view: "About")
    }
}
