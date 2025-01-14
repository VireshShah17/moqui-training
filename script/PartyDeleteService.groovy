import org.moqui.context.ExecutionContext
import org.moqui.entity.EntityList
import org.moqui.entity.EntityValue

ExecutionContext ec = context.ec

// Fetch the Party record
EntityValue partyRecord = null
if (context.partyId) {
        partyRecord = ec.entity.find("moqui.party.Party")
                .condition("partyId", context.partyId)
                .one()
}

// Fetch all related ContactMech records
EntityList contactRecords = null
if (context.partyId) {
        contactRecords = ec.entity.find("moqui.contactmech.ContactMech")
                .condition("partyId", context.partyId)
                .list()
}

// Fetch all related records of order_header
EntityList orderHeaders = null
if (context.partyId){
        orderHeaders = ec.entity.find("moqui.orderheader.OrderHeader")
                .condition("partyId", context.partyId).list()
}

// Delete all related ContactMech records first
if (contactRecords && !contactRecords.isEmpty()) {
        contactRecords.each { contactRecord ->
                contactRecord.delete()
                ec.message.addMessage("Deleted ContactMech record with partyId=${context.partyId}.", "info")
        }
}
// Delete all related OrderHeaders first
if (orderHeaders && !orderHeaders.isEmpty()){
        orderHeaders.each{orderHeader ->
                orderHeader.delete()
                ec.message.addMessage("Deleted OrderHeader record with partyId=${context.partyId}.", "info")
        }
}

if (partyRecord) {
        partyRecord.delete()
        ec.message.addMessage("Deleted Party record with partyId=${context.partyId}.", "info")
}

// Return the deleted partyId in the context
context.partyId = context.partyId
