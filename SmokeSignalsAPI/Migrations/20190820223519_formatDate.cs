using Microsoft.EntityFrameworkCore.Migrations;

namespace SmokeSignalsAPI.Migrations
{
    public partial class formatDate : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "Sent",
                table: "Messages",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Sent",
                table: "Messages");
        }
    }
}
